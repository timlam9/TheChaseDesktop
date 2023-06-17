package domain.models

import kotlinx.serialization.Serializable

data class GameQuestion(
    val title: String,
    val options: List<GameQuestionOption>,
    val showRightAnswer: Boolean
)
