package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.models.ChaseState
import domain.models.GameQuestion
import domain.models.GameQuestionOption
import domain.models.GameQuestionOption.SelectedBy.*

@Composable
fun BoardContent(state: ChaseState) {
    Row(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Board(state = state)
        QuestionSection(question = state.currentQuestion)
    }
}

@Composable
fun Board(
    state: ChaseState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.width(560.dp)) {
        state.board.forEach { chaseBox ->
            BoardRow(
                position = chaseBox.position,
                type = chaseBox.type,
                title = chaseBox.type.title
            )
        }
    }
}

@Composable
fun QuestionSection(
    question: GameQuestion,
    modifier: Modifier = Modifier,
) = with(question) {
    Column(modifier.fillMaxSize().padding(top = 20.dp)) {
        Text(text = "$id. $title", style = MaterialTheme.typography.h4, modifier = Modifier)
        Spacer(modifier = Modifier.height(40.dp))
        options.forEach {
            OptionCard(it, showRightAnswer, showPlayerAnswer, showChaserAnswer)
        }
        PlayersRow(
            showPlayer = options.map { it.selectedBy }.contains(PLAYER) || options.map { it.selectedBy }.contains(BOTH),
            showChaser = options.map { it.selectedBy }.contains(CHASER) || options.map { it.selectedBy }.contains(BOTH)
        )
    }
}

@Composable
fun OptionCard(
    question: GameQuestionOption,
    showRightAnswer: Boolean,
    showPlayerAnswer: Boolean,
    showChaserAnswer: Boolean
) = with(question) {
    println("BUG: $question")

    val paddingModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 6.dp)

    val borderModifier = when {
        (question.selectedBy == CHASER) && showChaserAnswer -> paddingModifier.border(
            width = 4.dp,
            color = Color.Red,
            shape = RoundedCornerShape(4)
        )

        (question.selectedBy == BOTH) && showChaserAnswer -> paddingModifier.border(
            width = 4.dp,
            color = Color.Red,
            shape = RoundedCornerShape(4)
        )

        else -> paddingModifier
    }

    Card(
        elevation = 2.dp,
        modifier = borderModifier,
        backgroundColor = when {
            showRightAnswer && isRightAnswer -> Color.Green
            (question.selectedBy == PLAYER) && showPlayerAnswer -> Color.Blue
            (question.selectedBy == BOTH) && showPlayerAnswer -> Color.Blue
            else -> MaterialTheme.colors.surface
        }
    ) {
        Text(
            text = "${position.name}. $title",
            modifier = Modifier.padding(30.dp),
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun PlayersRow(showPlayer: Boolean, showChaser: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AnimatedVisibility(showPlayer) {
            Card(
                elevation = 2.dp,
                modifier = Modifier.padding(8.dp),
                backgroundColor = Color.Blue
            ) {
                Text(
                    text = "Player",
                    modifier = Modifier.padding(30.dp),
                    style = MaterialTheme.typography.h5
                )
            }
        }
        AnimatedVisibility(showChaser) {
            Card(
                elevation = 2.dp,
                modifier = Modifier.padding(8.dp),
                backgroundColor = Color.Red
            ) {
                Text(
                    text = "Chaser",
                    modifier = Modifier.padding(30.dp),
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}
