package components

import domain.ChaseState
import androidx.compose.runtime.Composable
import domain.ChaseBox
import domain.RowType

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