package ui

import data.ClientBuilder
import data.Repository
import data.Test
import data.WebSocket
import domain.models.ChaseBox
import domain.models.ChaseState
import domain.GameBrain
import domain.models.ChaseBox.RowType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    private val brain = GameBrain()
    private val client = ClientBuilder.defaultHttpClient
    private val repository: Repository = Repository(client = client)
    private val webSocket: WebSocket = WebSocket(client = client, repository = repository)

    private var _chaseState = MutableStateFlow(ChaseState())
    val chaseState: StateFlow<ChaseState> = _chaseState.asStateFlow()

    init {
        CoroutineScope(dispatcher).launch {
            val token = repository.login("board@gmail.com", "password")
        }
    }

    fun onChaserClick() {
        val chaserBox = _chaseState.value.board.first { it.type == RowType.CHASER_HEAD }
        onChaseBoxClick(chaserBox)
    }

    fun onPlayerClick() {
        val playerBox = _chaseState.value.board.first { it.type == RowType.PLAYER_HEAD }
        onChaseBoxClick(playerBox)
    }

    fun onChaseBoxClick(chaseBox: ChaseBox) {
        val newState = when (chaseBox.type) {
            RowType.CHASER_HEAD -> brain.moveChaser(_chaseState.value, chaseBox)
            RowType.PLAYER_HEAD -> brain.movePlayer(_chaseState.value, chaseBox)
            else -> _chaseState.value
        }

        _chaseState.update { newState }
    }

    fun onChaseBoxLongClick(chaseBox: ChaseBox) {
        val newState = when (chaseBox.type) {
            RowType.CHASER_HEAD -> brain.moveChaserBack(_chaseState.value, chaseBox)
            RowType.PLAYER_HEAD -> brain.movePlayerBack(_chaseState.value, chaseBox)
            else -> _chaseState.value
        }

        _chaseState.update { newState }
    }

    fun onSetupClick(chaserName: String, playerName: String) {
        CoroutineScope(dispatcher).launch {
            webSocket.sendMessage(Test(chaserName + playerName))
        }
        _chaseState.update {
            brain.setupGame(it, chaserName, playerName)
        }
    }

    fun onResetClick() {
        _chaseState.update {
            brain.resetGame(it)
        }
    }
}
