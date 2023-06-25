package domain.models

data class ChaseState(
    val board: List<ChaseBox> = emptyList(),
    val gameStatus: GameStatus = GameStatus.SETUP,
    val currentQuestion: GameQuestion = GameQuestion(
        id = "",
        title = "",
        options = emptyList(),
        showRightAnswer = false,
        showPlayerAnswer = false,
        showChaserAnswer = false
    ),
    val final: ChaseFinal = ChaseFinal(
        timer = 120,
        startTimer = false,
        pauseTimer = false,
        resetTimer = 0,
        playersPoints = 0,
        chaserPoints = 0
    )
)
