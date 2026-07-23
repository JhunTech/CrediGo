package com.redfrogec.credigo.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class SecurityQuestion(
    val id: Long,
    val description: String = "",
    val creationDate: LocalDateTime = LocalDateTime(2025, 10, 6, 0, 0, 0),
    val active: Boolean = true
)