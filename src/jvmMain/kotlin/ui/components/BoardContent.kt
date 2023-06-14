package ui.components

import domain.models.state.ChaseState
import androidx.compose.runtime.Composable
import domain.models.box.ChaseBox
import domain.models.box.RowType

@Composable
fun BoardContent(
    state: ChaseState,
    onClick: (chaseBox: ChaseBox) -> Unit,
    onLongClick: (chaseBox: ChaseBox) -> Unit
) {
    state.board.forEach { chaseBox ->
        BoardRow(
            position = chaseBox.position,
            type = chaseBox.type,
            title = chaseBox.getTitle(state),
            onClick = { onClick(chaseBox) },
            onLongClick = { onLongClick(chaseBox) }
        )
    }
}

private fun ChaseBox.getTitle(state: ChaseState) = when (type) {
    RowType.CHASER_HEAD -> state.chaserName
    RowType.PLAYER_HEAD -> state.playerName
    else -> ""
}