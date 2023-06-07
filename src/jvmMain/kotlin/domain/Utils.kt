package domain

private const val HOME_POSITION = 8

internal fun Int.checkForGameOver(chaserPosition: Int): GameStatus = when {
    this == chaserPosition -> GameStatus.CHASER_WIN
    this == HOME_POSITION -> GameStatus.PLAYER_WIN
    else -> GameStatus.PLAYING
}
