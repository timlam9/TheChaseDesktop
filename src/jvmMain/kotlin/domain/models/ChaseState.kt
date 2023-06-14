package domain.models

import domain.models.ChaseBox.RowType
import domain.models.GameQuestionOption.Position
import domain.models.GameQuestionOption.SelectedBy

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

data class ChaseState(
    val board: List<ChaseBox> = initialList,
    val gameStatus: GameStatus = GameStatus.PLAYING,
    val currentQuestion: GameQuestion = GameQuestion(
        title = "Πόσους λάκους έχει η φάβα μέσα σε μία φασολάδα με κομένη και χαροκαμένη στραπατσάδα;",
        options = listOf(
            GameQuestionOption(
                title = "Η φάβα έχει 5 λάκκους",
                position = Position.A,
                selectedBy = SelectedBy.PLAYER
            ),
            GameQuestionOption(
                title = "Η φάβα έχει 50 λάκκους",
                position = Position.B,
                selectedBy = SelectedBy.NONE
            ),
            GameQuestionOption(
                title = "Η φάβα έχει 500 λάκκους",
                position = Position.C,
                selectedBy = SelectedBy.CHASER
            ),
        )
    )
)
