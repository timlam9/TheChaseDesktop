package domain.models

import domain.models.ChaseBox.RowType
import domain.models.GameQuestionOption.Position
import domain.models.GameQuestionOption.SelectedBy
import kotlinx.serialization.Serializable

internal val initialList = mutableListOf(
    ChaseBox(position = 0, type = RowType.CHASER_HEAD),
    ChaseBox(position = 1, type = RowType.EMPTY),
    ChaseBox(position = 2, type = RowType.EMPTY),
    ChaseBox(position = 3, type = RowType.PLAYER_HEAD),
    ChaseBox(position = 4, type = RowType.PLAYER),
    ChaseBox(position = 5, type = RowType.PLAYER),
    ChaseBox(position = 6, type = RowType.PLAYER),
    ChaseBox(position = 7, type = RowType.PLAYER),
    ChaseBox(position = 8, type = RowType.BANK),
)

@Serializable
data class ChaseState(
    val board: List<ChaseBox> = initialList,
    val gameStatus: GameStatus = GameStatus.PLAYING,
    val currentQuestion: GameQuestion = GameQuestion(
        title = "Πόσους ",
        options = listOf(
            GameQuestionOption(
                title = "5 λάκκους",
                position = Position.A,
                selectedBy = SelectedBy.PLAYER,
                isRightAnswer = false
            ),
            GameQuestionOption(
                title = "50 λάκκους",
                position = Position.B,
                selectedBy = SelectedBy.NONE,
                isRightAnswer = true
            ),
            GameQuestionOption(
                title = "500 λάκκους",
                position = Position.C,
                selectedBy = SelectedBy.CHASER,
                isRightAnswer = false
            ),
        ),
        showRightAnswer = false,
    )
)