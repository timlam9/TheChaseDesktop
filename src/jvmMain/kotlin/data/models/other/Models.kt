package data.models.other

import data.models.Position
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameAnswer(
    val email: String,
    val position: Position
)

@Serializable
data class ChaseState(
    val currentQuestionID: Int = 0,
    val playerAnswer: GameAnswer? = null,
    val gameState: GameState = GameState.PAUSED
)

@Serializable
enum class GameState {

    @SerialName("paused")
    PAUSED,
    @SerialName("playing")
    PLAYING
}