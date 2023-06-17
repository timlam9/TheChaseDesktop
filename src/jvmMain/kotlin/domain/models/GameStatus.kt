package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GameStatus {

    @SerialName("chaser_win")
    CHASER_WIN,
    @SerialName("player_win")
    PLAYER_WIN,
    @SerialName("playing")
    PLAYING,
    @SerialName("setup")
    SETUP
}
