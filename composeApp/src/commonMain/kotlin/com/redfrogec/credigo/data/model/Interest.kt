package com.redfrogec.credigo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Interest(
    val id: Int,
    val value: Double = 0.00,
    val description: String = "",
    val active: Boolean = true
)