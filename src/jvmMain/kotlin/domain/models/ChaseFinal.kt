package domain.models

data class ChaseFinal(
    val timer: Int,
    val startTimer: Boolean,
    val pauseTimer: Boolean,
    val resetTimer: Int,
    val playersPoints: Int,
    val chaserPoints: Int
)
