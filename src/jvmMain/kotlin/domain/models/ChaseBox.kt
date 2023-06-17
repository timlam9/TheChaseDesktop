package domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChaseBox(
    val position: Int,
    val type: RowType,
) {

    @Serializable
    enum class RowType(val title: String) {

        @SerialName("chaser")
        CHASER(""),
        @SerialName("chaser_head")
        CHASER_HEAD("Chaser"),
        @SerialName("player")
        PLAYER(""),
        @SerialName("player_head")
        PLAYER_HEAD("Player"),
        @SerialName("empty")
        EMPTY(""),
        @SerialName("bank")
        BANK("")
    }
}
