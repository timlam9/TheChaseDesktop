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
import ui.components.BoardContent
import ui.components.GameOverContent
import ui.components.SetupContent

@Composable
@Preview
fun MainScreen(state: ChaseState) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state.gameStatus) {
                GameStatus.PLAYING -> BoardContent(state = state)
                GameStatus.SETUP -> SetupContent()
                else -> GameOverContent(gameStatus = state.gameStatus)
            }
        }
        Spacer(Modifier.size(20.dp))
    }
}
