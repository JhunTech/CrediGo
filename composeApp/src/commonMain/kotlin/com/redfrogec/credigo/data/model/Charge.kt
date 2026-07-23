package com.redfrogec.credigo.data.model

data class Charge (
    val id: Long,
    val loanId: Long,
    val quotaNumber: Int,
    val chargeDate: String,
    val customerPaymentDate: String?,
    val quotaValue: Double,
    val chargeValue: Double,
    val remainingValue: Double,
    val chargeTypeId: Int
)

data class ChargePaid(
    val loanId: Long,
    val totalFeesPaid: Int,
    val totalAmountCharged: Double,
    val totalAmountRemaining: Double,
    val totalAmountQuota: Double
)

data class ChargePending(
    val id: Long,
    val chargeDate: String,
    val quotaValue: Double,
    val chargeValue: Double,
    val remainingValue: Double,
)

data class NumberCharge(
    val number: Int,
    val description: String
)
