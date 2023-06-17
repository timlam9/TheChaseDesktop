package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.models.GameStatus

@Composable
fun GameOverContent(gameStatus: GameStatus) {
    val text by remember(gameStatus) {
        mutableStateOf(
            when (gameStatus) {
                GameStatus.CHASER_WIN -> "chaser wins!"
                GameStatus.PLAYER_WIN -> "player wins!"
                else -> "Game Over"
            }
        )
    }

    val color by remember(gameStatus) {
        mutableStateOf(
            when (gameStatus) {
                GameStatus.CHASER_WIN -> Color.Red
                GameStatus.PLAYER_WIN -> Color.Blue
                else -> Color.Black
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(160.dp))
        Text(text, style = MaterialTheme.typography.h2.copy(color = color))
        Spacer(modifier = Modifier.size(40.dp))
        Button(onClick = {}) {
            Text("Next round")
        }
    }
}