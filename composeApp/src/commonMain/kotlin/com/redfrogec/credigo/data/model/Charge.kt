package com.redfrogec.credigo.data.model

data class Charge (
    val id: String,
    val loanId: String,
    val quotaNumber: Int,
    val chargeDate: String,
    val customerPaymentDate: String?,
    val quotaValue: Double,
    val chargeValue: Double,
    val remainingValue: Double
)

data class ChargePaid(
    val loanId: String,
    val totalFeesPaid: Int,
    val totalAmountCharged: Double
)
