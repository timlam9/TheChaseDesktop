package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.ChaseFinal
import domain.models.ChaseState
import domain.timer.CoroutinesTimerState
import ui.theme.ChaseBlue
import ui.theme.ChaseRed

@Composable
fun FinalContent(state: ChaseState, timerState: CoroutinesTimerState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        QuestionSection(shouldShowAnswers = false, question = state.currentQuestion, modifier = Modifier.fillMaxWidth())
        FinalTimer(state = state.final, timerState = timerState)
    }
}

@Composable
fun FinalTimer(state: ChaseFinal, timerState: CoroutinesTimerState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CountDownTimer(
            progress = timerState.progressPercentage,
            time = timerState.formattedTime
        )
        Text(text = "Players points: ${state.playersPoints}", style = MaterialTheme.typography.h4, color = ChaseBlue)
        Text(text = "Chaser points: ${state.chaserPoints}", style = MaterialTheme.typography.h4, color = ChaseRed)
    }
}
