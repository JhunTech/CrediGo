package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.data.model.PayCharge
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PayChargeViewModel(private val loanSDK: LoanSDK, private val chargeSDK: ChargeSDK): ViewModel() {
    private val _uiState = MutableStateFlow(PayCharge())
    val uiState: StateFlow<PayCharge> = _uiState

    private val _confirmPayment = MutableStateFlow(false)
    val confirmPayment: StateFlow<Boolean> = _confirmPayment

    fun onPaymentAmountChange(value: String) {
        val amount = value.toDoubleOrNull() ?: return
        _uiState.update {
            it.copy(
                paymentAmount = amount.coerceAtMost(it.pending)
            )
        }
    }

    fun loadState(charge: Charge){
        _uiState.value = PayCharge(
            loanId = charge.loanId,
            chargeId = charge.id,
            loanNumber = charge.id.toString(),
            totalDue = charge.quotaValue,
            alreadyPaid = charge.chargeValue,
            paymentAmount = charge.remainingValue
        )
    }

    fun confirmPayment() {
        println("Confirmed payment: ${_uiState.value.chargeId}")
        _confirmPayment.value = false
        val updateCharge = chargeSDK.updateDataCharge(
            _uiState.value.loanId,
            currentDateDisplay().toString(),
            _uiState.value.totalDue,
            _uiState.value.alreadyPaid + _uiState.value.paymentAmount,
            _uiState.value.totalDue - (_uiState.value.alreadyPaid + _uiState.value.paymentAmount),
            _uiState.value.chargeId
        )
        if(updateCharge > 0) {
            changeLoanState(_uiState.value.loanId)
            _confirmPayment.value = true
        }
    }

    fun changeLoanState(loanId: Long){
        val selectedLoan = loanSDK.selectLoanById(loanId)
        if(selectedLoan != null){
            val chargesPaid = chargeSDK.selectChargePaid(loanId)
            if (chargesPaid != null){
                if (chargesPaid.totalFeesPaid == selectedLoan.quotaNumbers) {
                    loanSDK.updateLoanState(false, loanId)
                }
            }
        }
    }
}