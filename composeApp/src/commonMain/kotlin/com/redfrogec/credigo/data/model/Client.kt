package com.redfrogec.credigo.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Client(
    val id: Int,
    val userId: Int,
    val image: String,
    val identification: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val blocked: Boolean,
    val registerDate: LocalDateTime,
)

enum class ClientStatus { Activo, Bloqueado }