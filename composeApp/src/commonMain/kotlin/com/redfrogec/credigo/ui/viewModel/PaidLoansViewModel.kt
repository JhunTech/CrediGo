package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.ChargePending
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.loanUI
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus

class PaidLoansViewModel(private val loanSDK: LoanSDK) : ViewModel() {

    lateinit var navigation: NavController
    private val settings: Settings = Settings()

    private val _paidLoansUI = MutableStateFlow<List<loanUI>>(emptyList())
    val paidLoansUI: StateFlow<List<loanUI>?> = _paidLoansUI.asStateFlow()

    init {
        viewModelScope.launch {
            loadLoans()
        }
    }

    fun loadLoans() {
        val userId = settings.getInt(Constants.USER_ID, 0)
        val paidLoans = loanSDK.selectAllLoansByUserId(userId.toLong(), false)
        if (paidLoans.isNotEmpty()) {
            paidLoans.forEach { loan ->
                val loanUI = loanUI(
                    id = loan.id,
                    clientName = loan.clientName,
                    loanNumber = "Prest. #${loan.id}",
                    value = loan.value,
                    quotaInfo = "${loan.quotaNumbers}/${loan.quotaNumbers}",
                    dueInfo = "Pagado",
                    isPaid = loan.active
                )
                _paidLoansUI.value += loanUI
            }
        }
    }

    fun onBackClicked() {
        println("Return active loans screen")
        navigation.popBackStack()
    }
}