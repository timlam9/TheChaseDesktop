package domain.models.state

data class GamePlayer(
    val position: Int,
    val name: String,
    val type: PlayerType,
)

enum class PlayerType {

    CHASER,
    PLAYER,
}
