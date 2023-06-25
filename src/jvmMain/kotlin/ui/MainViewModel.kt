package ui

import data.ClientBuilder
import data.Repository
import data.webSocket.SocketEvent
import data.webSocket.SocketMessage
import data.webSocket.WebSocket
import domain.models.ChaseState
import domain.timer.CoroutinesTimer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val client = ClientBuilder.defaultHttpClient
    private val repository: Repository = Repository(client = client)
    private val webSocket: WebSocket = WebSocket(client = client, repository = repository)

    private val timerCoroutine = CoroutineScope(dispatcher)
    var timer = CoroutinesTimer(timerCoroutine)

    private val socketEvents: SharedFlow<SocketEvent> = webSocket.socketEvents.shareIn(
        scope = CoroutineScope(context = dispatcher),
        started = SharingStarted.WhileSubscribed()
    )

    val chaseState: StateFlow<ChaseState> = webSocket.chaseState

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

        chaseState.map { it.final.resetTimer }.distinctUntilChanged().onEach {
            println("Reset timer")
            timer.reset()
        }.launchIn(CoroutineScope(dispatcher))

        chaseState.map { it.final.timer }.distinctUntilChanged().onEach {
            println("Set timer")
            timer.setTimer(it * 1000L)

        }.launchIn(CoroutineScope(dispatcher))

        chaseState.map { it.final.pauseTimer }.distinctUntilChanged().onEach {
            println("Pause timer: $it")
            if (it) timer.pause() else timer.resume()
        }.launchIn(CoroutineScope(dispatcher))
    }

    fun startWebSocketConnection() {
        webSocket.startWebSocket()
    }

    fun closeWebSocketConnection() {
        webSocket.closeWebSocket()
    }
}
