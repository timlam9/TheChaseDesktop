package data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int,
    val email: String,
    val displayName: String,
    val passwordHash: String
) : java.io.Serializable