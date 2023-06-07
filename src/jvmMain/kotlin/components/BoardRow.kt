package components

import androidx.compose.foundation.ExperimentalFoundationApi
import domain.RowType
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private const val STROKE = 8f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoardRow(
    position: Int,
    modifier: Modifier = Modifier,
    type: RowType = RowType.PLAYER,
    title: String = "",
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .drawWithCache {
                val topSpace = (size.width / 10) + (position * 28f)
                val bottomSpace = (size.width / 8) + (position * 28f)

                val topRightPointX = size.width - topSpace
                val topRightPointY = 0f

                val topLeftPointX = topSpace
                val topLeftPointY = 0f

                val bottomRightPointX = size.width - bottomSpace
                val bottomRightPointY = size.height

                val bottomLeftPointX = bottomSpace
                val bottomLeftPointY = size.height

                val path = Path()
                path.moveTo(topLeftPointX, topLeftPointY)
                path.lineTo(topRightPointX, topRightPointY)
                path.lineTo(bottomRightPointX, bottomRightPointY)
                path.lineTo(bottomLeftPointX, bottomLeftPointY)
                path.close()

                val fillColor = when (type) {
                    RowType.CHASER -> Color.Red
                    RowType.CHASER_HEAD -> Color.Red
                    RowType.PLAYER -> Color.Blue
                    RowType.PLAYER_HEAD -> Color.Blue
                    RowType.EMPTY -> Color.Gray
                    RowType.HOME -> Color.Black
                }

                onDrawBehind {
                    drawPath(
                        style = Stroke(
                            cap = StrokeCap.Round,
                            width = STROKE,
                            join = StrokeJoin.Round
                        ),
                        path = path,
                        color = Color.Black,
                    )
                    drawPath(
                        style = Fill,
                        path = path,
                        color = fillColor,
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        when (type) {
            RowType.CHASER_HEAD, RowType.PLAYER_HEAD -> {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4.copy(color = Color.White, fontWeight = FontWeight.Bold),
                    modifier = Modifier.offset(y = -(10.dp)),
                )
                Icon(
                    modifier = Modifier.width(120.dp).height(60.dp).offset(y = 20.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "",
                )
            }

            RowType.HOME -> {
                Text(
                    text = "HOME",
                    style = MaterialTheme.typography.h4.copy(color = Color.White, fontWeight = FontWeight.Bold),
                )
            }

            else -> Unit
        }
    }
}
