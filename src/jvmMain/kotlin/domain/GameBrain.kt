package domain

import domain.models.ChaseState
import domain.models.GameStatus
import domain.models.ChaseBox
import domain.models.ChaseBox.RowType

private const val HOME_POSITION = 8

class GameBrain {

    fun movePlayer(chaseState: ChaseState, chaseBox: ChaseBox): ChaseState {
        val board = chaseState.board
        val newList = chaseState.board.toMutableList()

        val currentPosition = chaseBox.position
        val nextPosition = chaseBox.position + 1
        val chaserPosition = board.first { it.type == RowType.CHASER_HEAD }.position

        newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.EMPTY)
        newList[nextPosition] = ChaseBox(position = nextPosition, type = RowType.PLAYER_HEAD)

        return chaseState.copy(
            gameStatus = checkForGameOver(nextPosition, chaserPosition),
            board = newList
        )
    }

    fun moveChaser(chaseState: ChaseState, chaseBox: ChaseBox): ChaseState {
        val board = chaseState.board
        val newList = chaseState.board.toMutableList()

        val currentPosition = chaseBox.position
        val nextPosition = chaseBox.position + 1
        val playerPosition = board.first { it.type == RowType.PLAYER_HEAD }.position

        newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.CHASER)
        newList[nextPosition] = ChaseBox(position = nextPosition, type = RowType.CHASER_HEAD)

        return chaseState.copy(
            gameStatus = checkForGameOver(playerPosition, nextPosition),
            board = newList
        )
    }

    fun movePlayerBack(chaseState: ChaseState, chaseBox: ChaseBox): ChaseState {
        val board = chaseState.board
        val newList = chaseState.board.toMutableList()

        val currentPosition = chaseBox.position
        val prePosition = chaseBox.position - 1
        val chaserPosition = board.first { it.type == RowType.CHASER_HEAD }.position

        if (prePosition <= chaserPosition) return chaseState

        newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.PLAYER)
        newList[prePosition] = ChaseBox(position = prePosition, type = RowType.PLAYER_HEAD)

        return chaseState.copy(board = newList)
    }

    fun moveChaserBack(chaseState: ChaseState, chaseBox: ChaseBox): ChaseState {
        val newList = chaseState.board.toMutableList()

        val currentPosition = chaseBox.position
        val prePosition = chaseBox.position - 1

        if (currentPosition <= 0) return chaseState

        newList[currentPosition] = ChaseBox(position = currentPosition, type = RowType.EMPTY)
        newList[prePosition] = ChaseBox(position = prePosition, type = RowType.CHASER_HEAD)

        return chaseState.copy(board = newList)
    }

    fun setupGame(chaseState: ChaseState, chaserName: String, playerName: String): ChaseState {
        return chaseState.copy(
            board = emptyList(),
            gameStatus = GameStatus.PLAYING
        )
    }

    fun resetGame(chaseState: ChaseState): ChaseState {
        return chaseState.copy(
            board = emptyList(),
            gameStatus = GameStatus.SETUP
        )
    }

    private fun checkForGameOver(playerPosition: Int, chaserPosition: Int): GameStatus = when (playerPosition) {
        chaserPosition -> GameStatus.CHASER_WIN
        HOME_POSITION -> GameStatus.PLAYER_WIN
        else -> GameStatus.PLAYING
    }
}
