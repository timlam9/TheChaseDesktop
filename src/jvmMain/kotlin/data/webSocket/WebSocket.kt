package data.webSocket

import com.google.gson.Gson
import com.google.gson.JsonParser
import data.ClientBuilder
import data.Repository
import domain.models.ChaseState
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*

class WebSocket(
    private val client: HttpClient = ClientBuilder.defaultHttpClient,
    private val repository: Repository = Repository(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val gson: Gson = Gson()
) {

    private val _socketEventChannel: Channel<SocketEvent> = Channel()
    val socketEvents: Flow<SocketEvent>
        get() = _socketEventChannel.receiveAsFlow().flowOn(dispatcher)

    private var _chaseState = MutableStateFlow(ChaseState())
    val chaseState = _chaseState.asStateFlow()

    private var webSocketSession: DefaultClientWebSocketSession? = null
    private lateinit var socketConnectJob: CompletableJob

    private val socketExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleSocketError(exception)
    }

    private val childExceptionHandler = CoroutineExceptionHandler { _, exception ->
        handleSocketError(exception)
    }

    fun startWebSocket() {
        println("Open connection")

        CoroutineScope(dispatcher).launch {
            val token = repository.login("board@gmail.com", "password")
            openConnection(token)
        }
    }

    private fun openConnection(token: String) {
        socketConnectJob = Job()

        CoroutineScope(dispatcher).launch {
            _socketEventChannel.send(SocketEvent.ConnectionOpened)
        }

        CoroutineScope(dispatcher + socketConnectJob).launch(socketExceptionHandler) {
            supervisorScope {
                client.webSocket(
                    method = HttpMethod.Get,
                    host = "192.168.1.101",
                    port = 8080,
                    path = "/thechase",
                    request = { header("Authorization", "Bearer $token") }
                ) {
                    webSocketSession = this
                    _socketEventChannel.send(SocketEvent.ConnectionOpened)

                    val messagesReceivedRoutine = launch(childExceptionHandler) {
                        observeSocketMessages()
                    }
                    messagesReceivedRoutine.join()
                }
            }
        }
    }

    suspend fun sendMessage(baseModel: SocketMessage.OutBound) {
        val message = gson.toJson(baseModel)
        println("Send message: $message to socket: $webSocketSession")
        webSocketSession?.send(Frame.Text(message))
    }

    fun closeWebSocket() {
        webSocketSession = null
        client.close()
        println("Connection closed.")
    }

    private suspend fun DefaultClientWebSocketSession.observeSocketMessages() {
        incoming.consumeEach { frame ->
            println("Frame received: $frame")

            if (frame is Frame.Text) {
                val receivedText = frame.readText()
                println("Frame text received: $receivedText")

                updateState(receivedText)
            } else {
                println("- - - - - - - - - - > Unknown Message received: $frame")
            }
        }
    }

    private fun updateState(receivedText: String) {
        try {
            val jsonObject = JsonParser.parseString(receivedText).asJsonObject

            val type = when (jsonObject.get("type").asString) {
                "state" -> SocketMessage.InBound.State::class.java
                else -> SocketMessage::class.java
            }

            val payload: SocketMessage = gson.fromJson(receivedText, type)
            println("- - - - - - - - - - > Message received: $payload")

            when (payload) {
                is SocketMessage.InBound.SocketError -> TODO()
                is SocketMessage.InBound.State -> _chaseState.update { payload.chaseState }
                is SocketMessage.OutBound.Connect -> TODO()
                is SocketMessage.OutBound.Disconnect -> TODO()
            }
        } catch (e: Exception) {
            println("- - - - - - - - - - > Message decode exception: ${e.localizedMessage}")
        }
    }

    private fun handleSocketError(throwable: Throwable) {
        CoroutineScope(dispatcher).launch {
            _socketEventChannel.send(SocketEvent.ConnectionClosed(null))
        }
        retryToConnect()
    }

    private fun retryToConnect() {
        CoroutineScope(dispatcher).launch {
            delay(SOCKET_CONNECT_RETRY_INTERVAL)
            _socketEventChannel.send(SocketEvent.RetryingToConnect)

            val token = repository.login("board@gmail.com", "password")
            openConnection(token)
        }
    }

    companion object {

        private const val SOCKET_CONNECT_RETRY_INTERVAL = 5000L
    }
}
