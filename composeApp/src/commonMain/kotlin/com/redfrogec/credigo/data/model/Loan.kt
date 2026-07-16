package com.redfrogec.credigo.data.model

import kotlinx.datetime.LocalDate

data class Loan(
    val id: Long,
    val clientId: Long,
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

data class LoanUI(
    val id: Long,
    val clientName: String,
    val loanNumber: String,
    val value: Double,
    val quotaInfo: String,
    val dueInfo: String,
    val isPaid: Boolean
)

data class LoanDate(
    val monthNumber: Int,
    val month: String,
    var value: Float
)