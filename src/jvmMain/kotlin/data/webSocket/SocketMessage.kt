package data.webSocket

import domain.models.ChaseState

sealed class SocketMessage {

    sealed class OutBound: SocketMessage() {

        data class Connect(val type: String = "connect", val email: String) : OutBound()

        data class Disconnect(val type: String = "disconnect", val email: String) : OutBound()
    }

    sealed class InBound: SocketMessage() {

        data class SocketError(val type: String = "socket_error", val errorType: String) : InBound()

        data class State(val type: String = "state", val chaseState: ChaseState) : InBound()
    }
}
