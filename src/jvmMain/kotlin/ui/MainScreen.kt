package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.ChaseState
import domain.models.GameStatus
import domain.timer.CoroutinesTimerState
import ui.components.BoardContent
import ui.components.FinalContent
import ui.components.GameOverContent
import ui.components.SetupContent

@Composable
@Preview
fun MainScreen(state: ChaseState, timerState: CoroutinesTimerState) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.gameStatus) {
                GameStatus.PLAYING -> BoardContent(state = state)
                GameStatus.SETUP -> SetupContent()
                GameStatus.CHASER_WIN -> GameOverContent(gameStatus = state.gameStatus)
                GameStatus.PLAYER_WIN -> GameOverContent(gameStatus = state.gameStatus)
                GameStatus.FINAL -> FinalContent(state= state, timerState = timerState)
            }
        }
        Spacer(Modifier.size(20.dp))
    }
}
