import domain.*
import domain.checkForGameOver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel {
    private var _chaseState = MutableStateFlow(ChaseState())
    val chaseState: StateFlow<ChaseState> = _chaseState.asStateFlow()

    fun onChaserClick() {
        val chaserBox = _chaseState.value.board.first { it.type == RowType.CHASER_HEAD }
        onChaseBoxClick(chaserBox)
    }

    fun onPlayerClick() {
        val playerBox = _chaseState.value.board.first { it.type == RowType.PLAYER_HEAD }
        onChaseBoxClick(playerBox)
    }

    fun onResetClick() {
        _chaseState.update {
            it.copy(
                board = initialList,
                gameStatus = GameStatus.SETUP
            )
        }
    }

    fun onChaseBoxClick(chaseBox: ChaseBox) {
        val board = _chaseState.value.board
        val newList = _chaseState.value.board.toMutableList()

        when (chaseBox.type) {
            RowType.CHASER_HEAD -> {
                val currentPosition = chaseBox.position
                val nextPosition = chaseBox.position + 1
                val playerPosition = board.first { it.type == RowType.PLAYER_HEAD }.position

                newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.CHASER)
                newList[nextPosition] = ChaseBox(position = nextPosition, type = RowType.CHASER_HEAD)

                _chaseState.update {
                    it.copy(gameStatus = playerPosition.checkForGameOver(nextPosition))
                }
            }

            RowType.PLAYER_HEAD -> {
                val currentPosition = chaseBox.position
                val nextPosition = chaseBox.position + 1
                val chaserPosition = board.first { it.type == RowType.CHASER_HEAD }.position

                newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.EMPTY)
                newList[nextPosition] = ChaseBox(position = nextPosition, type = RowType.PLAYER_HEAD)

                _chaseState.update {
                    it.copy(gameStatus = nextPosition.checkForGameOver(chaserPosition))
                }
            }

            else -> Unit
        }

        _chaseState.update { it.copy(board = newList) }
    }

    fun onSetupClick(chaserName: String, playerName: String) {
        _chaseState.update {
            it.copy(
                board = initialList,
                gameStatus = GameStatus.PLAYING,
                chaserName = chaserName,
                playerName = playerName
            )
        }
    }

    fun onChaseBoxLongClick(chaseBox: ChaseBox) {
        val board = _chaseState.value.board
        val newList = _chaseState.value.board.toMutableList()

        when (chaseBox.type) {
            RowType.CHASER_HEAD -> {
                val currentPosition = chaseBox.position
                val prePosition = chaseBox.position - 1

                if(currentPosition <= 0) return

                newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.EMPTY)
                newList[prePosition] = ChaseBox(position = prePosition, type = RowType.CHASER_HEAD)
            }

            RowType.PLAYER_HEAD -> {
                val currentPosition = chaseBox.position
                val prePosition = chaseBox.position - 1
                val chaserPosition = board.first { it.type == RowType.CHASER_HEAD }.position

                if (prePosition <= chaserPosition) return

                newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.PLAYER)
                newList[prePosition] = ChaseBox(position = prePosition, type = RowType.PLAYER_HEAD)
            }
            else -> Unit
        }

        _chaseState.update { it.copy(board = newList) }
    }
}
