package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.models.box.ChaseBox
import domain.models.state.ChaseState
import domain.models.state.GameStatus
import ui.components.BoardContent
import ui.components.GameOverContent
import ui.components.SetupContent

@Composable
@Preview
fun MainScreen(
    state: ChaseState,
    onClick: (chaseBox: ChaseBox) -> Unit,
    onLongClick: (chaseBox: ChaseBox) -> Unit,
    onSetupClick: (String, String) -> Unit,
    onResetClick: () -> Unit,
) {
    MaterialTheme {

        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("The Chase", style = MaterialTheme.typography.h4)
            Spacer(Modifier.size(20.dp))
            when (state.gameStatus) {
                GameStatus.PLAYING -> BoardContent(
                    state = state,
                    onClick = onClick,
                    onLongClick = onLongClick
                )

                GameStatus.SETUP -> SetupContent(onStartClick = onSetupClick)
                else -> GameOverContent(
                    gameStatus = state.gameStatus,
                    onClick = onResetClick
                )
            }
        }
        Spacer(Modifier.size(20.dp))
    }
}
