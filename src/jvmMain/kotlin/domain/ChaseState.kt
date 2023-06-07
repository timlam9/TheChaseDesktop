package domain

internal val initialList = mutableListOf(
    ChaseBox(position = 0, type = RowType.CHASER_HEAD),
    ChaseBox(position = 1, type = RowType.EMPTY),
    ChaseBox(position = 2, type = RowType.EMPTY),
    ChaseBox(position = 3, type = RowType.PLAYER_HEAD),
    ChaseBox(position = 4, type = RowType.PLAYER),
    ChaseBox(position = 5, type = RowType.PLAYER),
    ChaseBox(position = 6, type = RowType.PLAYER),
    ChaseBox(position = 7, type = RowType.PLAYER),
    ChaseBox(position = 8, type = RowType.HOME),
)

data class ChaseState(
    val board: List<ChaseBox> = initialList,
    val gameStatus: GameStatus = GameStatus.SETUP,
    val chaserName: String = "",
    val playerName: String = "",
)