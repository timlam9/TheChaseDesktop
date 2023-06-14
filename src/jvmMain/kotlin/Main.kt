import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import ui.MainScreen
import ui.MainViewModel
import java.util.*

private val SCREEN_WIDTH = 600.dp
private val SCREEN_HEIGHT = 900.dp

private val viewModel = MainViewModel()

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
    Box(modifier = Modifier.size(width = SCREEN_WIDTH, height = SCREEN_HEIGHT)) {
        val state by viewModel.chaseState.collectAsState()

        MainScreen(
            state = state,
            onClick = viewModel::onChaseBoxClick,
            onLongClick = viewModel::onChaseBoxLongClick,
            onSetupClick = viewModel::onSetupClick,
            onResetClick = viewModel::onResetClick
        )
    }

    LaunchedEffect(Unit) {
        println(window.insets)
    }
}
