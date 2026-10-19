package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.LoanUI
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaidLoansViewModel(private val loanSDK: LoanSDK) : ViewModel() {

    lateinit var navigation: NavController
    private val settings: Settings = Settings()

    private val _paidLoansUI = MutableStateFlow<List<LoanUI>>(emptyList())
    val paidLoansUI: StateFlow<List<LoanUI>?> = _paidLoansUI.asStateFlow()

    init {
        viewModelScope.launch {
            loadLoans()
        }
    }

    fun loadLoans() {
        val userId = settings.getLong(Constants.USER_ID, 0)
        val paidLoans = loanSDK.selectAllLoansByUserId(userId, false)
        if (paidLoans.isNotEmpty()) {
            paidLoans.forEach { loan ->
                val loanUI = LoanUI(
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