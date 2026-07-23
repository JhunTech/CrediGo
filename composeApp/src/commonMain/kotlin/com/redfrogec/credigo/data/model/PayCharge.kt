package com.redfrogec.credigo.data.model

data class PayCharge (
    val loanId: Long = 0,
    val chargeId: Long = 0,
    val loanNumber: String = "Prest. #1",
    val totalDue: Double = 200.0,
    val alreadyPaid: Double = 50.0,
    val paymentAmount: Double = 150.0
) {
    val pending: Double
        get() = totalDue - alreadyPaid
}

