package com.redfrogec.credigo.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val image: String?,
    val email: String,
    val name: String,
    val passwordHash: String,
    val questionId: Long,
    val response: String,
    val registerDate: LocalDateTime,
    val updateDate: LocalDateTime?,
    val tokenId: Long?,
    val token: String?,
    val tokenExpire: LocalDateTime?,
)