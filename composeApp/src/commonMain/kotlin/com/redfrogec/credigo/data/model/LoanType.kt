package com.redfrogec.credigo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoanType(
    val id: Int,
    val description: String = "",
    val creationDate: String = "",
)
