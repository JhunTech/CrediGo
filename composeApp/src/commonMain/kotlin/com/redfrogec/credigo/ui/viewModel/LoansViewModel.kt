package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.ChargePending
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.Loan
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
import kotlinx.datetime.toLocalDateTime

class LoansViewModel(private val loanSDK: LoanSDK, private val chargeSDK: ChargeSDK, private val sharedViewModel: SharedViewModel) : ViewModel() {

    lateinit var navigation: NavController
    private val settings: Settings = Settings()
    var selectedTab by mutableStateOf(0) // 0 = Active, 1 = Paid
        private set

    private val _activeLoansUI = MutableStateFlow<List<loanUI>>(emptyList())
    val activeLoansUI: StateFlow<List<loanUI>?> = _activeLoansUI.asStateFlow()

    private val _paidLoansUI = MutableStateFlow<List<loanUI>>(emptyList())
    val paidLoansUI: StateFlow<List<loanUI>?> = _paidLoansUI.asStateFlow()

    init {
        viewModelScope.launch {
            //_externalSearchClient.value = settings.getBoolean(Constants.EXTERNAL_SEARCH_CLIENT, false)
            loadLoans()
        }
    }

    fun loadLoans() {
        val userId = settings.getInt(Constants.USER_ID, 0)
        val activeLoans = loanSDK.selectAllLoansByUserId(userId.toLong(), true)
        val paidLoans = loanSDK.selectAllLoansByUserId(userId.toLong(), false)
        if (activeLoans.isNotEmpty()) {
            activeLoans.forEach { loan ->
                val chargesPaid = chargeSDK.selectChargePaid(loan.id)
                val chargesNoPaid = chargeSDK.selectChargeNoPaid(loan.id)
                val nextPendingQuotaByLoan: ChargePending? = chargeSDK.nextPendingQuotaByLoan(loan.id)
                val quotaInfo = if (chargesPaid.isNotEmpty()){
                    "${chargesPaid.count()+1}/${activeLoans.count()}"
                } else{
                    "1/${activeLoans.count()}"
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
                val loanUI: loanUI = loanUI(
                    id = loan.id,
                    clientName = loan.clientName,
                    loanNumber = "Prest. #${loan.id}",
                    value = loan.value,
                    quotaInfo = "${quotaInfo}/${loan.quotaNumbers}",
                    dueInfo = dueInfo,
                    isPaid = loan.active
                )
                _paidLoansUI.value += loanUI
            }
        }
        if (paidLoans.isNotEmpty()) {
            paidLoans.forEach { loan ->
                val loanUI: loanUI = loanUI(
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

    fun onTabSelected(index: Int) {
        selectedTab = index
    }

    fun onAddClick() {
        settings.putInt(Constants.LOAN_ID, -1)
        navigation.navigate("newLoan")
    }
}