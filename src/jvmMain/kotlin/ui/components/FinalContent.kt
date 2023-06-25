package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Players points: ${state.playersPoints}", style = MaterialTheme.typography.h4, color = ChaseBlue)
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(state.playersPoints) {
                Card(
                    elevation = 2.dp,
                    modifier = Modifier.padding(2.dp).height(120.dp).width(60.dp),
                    backgroundColor = ChaseBlue
                ) {
                    Text(
                        text = it.toString(),
                        modifier = Modifier.padding(top = 20.dp),
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(40.dp))
        Text(text = "Chaser points: ${state.chaserPoints}", style = MaterialTheme.typography.h4, color = ChaseRed)
        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(state.chaserPoints) {
                Card(
                    elevation = 2.dp,
                    modifier = Modifier.padding(2.dp).height(120.dp).width(60.dp),
                    backgroundColor = ChaseRed
                ) {
                    Text(
                        text = it.toString(),
                        modifier = Modifier.padding(top = 20.dp),
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
