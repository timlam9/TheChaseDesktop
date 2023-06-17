package data.webSocket

import io.ktor.websocket.*

sealed class SocketEvent {

    object ConnectionOpened : SocketEvent()

    data class ConnectionClosed(val closeReason: CloseReason?) : SocketEvent()

    object RetryingToConnect : SocketEvent()
}