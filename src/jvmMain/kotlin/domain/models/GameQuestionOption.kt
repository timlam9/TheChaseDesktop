package domain.models

data class GameQuestionOption(
    val title: String,
    val position: Position,
    val selectedBy: SelectedBy
) {

    enum class Position {
        A,
        B,
        C
    }

    enum class SelectedBy {
        CHASER,
        PLAYER,
        BOTH,
        NONE
    }
}