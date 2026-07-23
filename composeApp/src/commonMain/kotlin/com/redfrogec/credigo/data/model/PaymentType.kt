package com.redfrogec.credigo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PaymentType(
    val id: Int,
    val description: String = "",
    val days: Int = 0,
    val active: Boolean = true
)