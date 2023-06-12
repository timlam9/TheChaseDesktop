import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import components.BoardContent
import components.GameOverContent
import components.SetupContent
import data.Repository
import domain.GameStatus
import java.util.*

private val SCREEN_WIDTH = 600.dp
private val SCREEN_HEIGHT = 900.dp

private val viewModel = MainViewModel()

private val repository: Repository = Repository()

@OptIn(ExperimentalComposeUiApi::class)
fun main() = singleWindowApplication(
    state = WindowState(size = DpSize(Dp.Unspecified, Dp.Unspecified)),
    onKeyEvent = {
        if (it.type == KeyEventType.KeyDown && it.isCtrlPressed) {
            when (it.key) {
                Key.R -> {
                    viewModel.onResetClick()
                    true
                }

                Key.C -> {
                    viewModel.onChaserClick()
                    true
                }

                Key.P -> {
                    viewModel.onPlayerClick()
                    true
                }

                else -> false
            }
        } else {
            false
        }
    }
) {
    Box(
        modifier = Modifier.size(width = SCREEN_WIDTH, height = SCREEN_HEIGHT)
    ) {
        App()
    }

    LaunchedEffect(Unit) {
        val token = repository.login("board@gmail.com","password")
        println("Token: $token")

        val users = repository.getUsers()
        println("Users: $users")

        val questions = repository.getQuestionsResponse()
        println("Questions: $questions")

        println(window.insets)
    }
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        val state by viewModel.chaseState.collectAsState()

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
                    onClick = viewModel::onChaseBoxClick,
                    onLongClick = viewModel::onChaseBoxLongClick
                )

                GameStatus.SETUP -> SetupContent(onStartClick = viewModel::onSetupClick)
                else -> GameOverContent(
                    gameStatus = state.gameStatus,
                    onClick = viewModel::onResetClick
                )
            }
        }
        Spacer(Modifier.size(20.dp))
    }
}
