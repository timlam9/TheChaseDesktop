package ui

import data.ClientBuilder
import data.Repository
import data.webSocket.SocketEvent
import data.webSocket.SocketMessage
import data.webSocket.WebSocket
import domain.models.ChaseState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val client = ClientBuilder.defaultHttpClient
    private val repository: Repository = Repository(client = client)
    private val webSocket: WebSocket = WebSocket(client = client, repository = repository)


    val chaseState: StateFlow<ChaseState> = webSocket.chaseState
    val socketEvents: SharedFlow<SocketEvent> = webSocket.socketEvents.shareIn(
        scope = CoroutineScope(context = dispatcher),
        started = SharingStarted.WhileSubscribed()
    )

    init {
        startWebSocketConnection()

        socketEvents.onEach { event ->
            when (event) {
                is SocketEvent.ConnectionClosed -> println("Connection closed")
                SocketEvent.ConnectionOpened -> {
                    println("Connection opened")
                    webSocket.sendMessage(SocketMessage.OutBound.Connect(email = "board@gmail.com"))
                }

                SocketEvent.RetryingToConnect -> println("Connection retrying")
            }
        }.launchIn(CoroutineScope(dispatcher))
    }

    fun startWebSocketConnection() {
        webSocket.startWebSocket()
    }
    fun closeWebSocketConnection() {
        webSocket.closeWebSocket()
    }
}
