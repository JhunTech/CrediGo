package com.redfrogec.credigo.domain.utils

import com.redfrogec.credigo.data.model.ChargePending
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.loanUI
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import org.koin.core.component.KoinComponent

class LoanUtils(private val loanSDK: LoanSDK, private val chargeSDK: ChargeSDK) : KoinComponent {
    fun loadLoansInfo(): List<loanUI> {
        val loansUI: List<loanUI> = emptyList()
        val userId = 0L//settings.getLong(Constants.USER_ID, 0)
        val activeLoans = loanSDK.selectAllLoansByUserId(userId, true)
        if (activeLoans.isNotEmpty()) {
            activeLoans.forEach { loan ->
                val chargesPaid = chargeSDK.selectChargePaid(loan.id)
                val chargesNoPaid = chargeSDK.selectChargeNoPaid(loan.id)
                val nextPendingQuotaByLoan: ChargePending? = chargeSDK.nextPendingQuotaByLoan(loan.id)
                val selectAllChargeByLoanId = chargeSDK.selectAllChargeByLoanId(loan.id)
                val quotaInfo = if (chargesPaid != null){
                    if (chargesPaid.totalFeesPaid == loan.quotaNumbers) {
                        "${chargesPaid.totalFeesPaid}/${loan.quotaNumbers}"
                    }
                    else{
                        "${chargesPaid.totalFeesPaid+1}/${loan.quotaNumbers}"
                    }
                } else{
                    "1/${loan.quotaNumbers}"
                }
                var dueInfo: String = ""
                if (nextPendingQuotaByLoan != null) {
                    val dateMinus = LocalDate.parse(nextPendingQuotaByLoan.chargeDate).minus(currentDateDisplay().date)
                    dueInfo = if(dateMinus.days > 0){
                        "Cobrar en ${dateMinus.days} días"
                    } else{
                        "Atrasado con ${dateMinus.days*-1} días"
                    }
                }
                val loanUI = loanUI(
                    id = loan.id,
                    clientName = loan.clientName,
                    loanNumber = "Prest. #${loan.id}",
                    value = loan.value,
                    quotaInfo = quotaInfo,
                    dueInfo = dueInfo,
                    isPaid = loan.active
                )
                loansUI.plus(loanUI)
            }
        }
        return loansUI
    }
}
