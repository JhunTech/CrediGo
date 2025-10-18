package com.redfrogec.credigo.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val imagen: String?,
    val email: String,
    val name: String,
    val passwordHash: String,
    val questionId: Int,
    val response: String,
    val registerDate: LocalDateTime,
    val updateDate: LocalDateTime?,
    val tokenId: Int?,
    val token: String?,
    val tokenExpire: LocalDateTime?,
)