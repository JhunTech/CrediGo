package com.redfrogec.credigo.data.model

data class Loan(
    val id: String,
    val clientId: String,
    val clientName: String,
    val value: Double,
    val paymentTypeId: Int,
    val interestId: Int,
    val quotaNumbers: Int,
    val active: Boolean,
    val creationDate: String,
    val deliveryDate: String,
    val loanTypeId: Int
)