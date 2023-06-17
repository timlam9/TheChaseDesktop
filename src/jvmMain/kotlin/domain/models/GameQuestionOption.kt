package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameQuestionOption(
    val title: String,
    val position: Position,
    val selectedBy: SelectedBy,
    val isRightAnswer: Boolean
) {

    @Serializable
    enum class Position {

        @SerialName("a")
        A,
        @SerialName("b")
        B,
        @SerialName("c")
        C
    }

    enum class SelectedBy {

        @SerialName("chaser")
        CHASER,
        @SerialName("player")
        PLAYER,
        @SerialName("none")
        NONE
    }
}