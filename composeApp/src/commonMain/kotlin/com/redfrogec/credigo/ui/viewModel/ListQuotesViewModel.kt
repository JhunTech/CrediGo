package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListQuotesViewModel(private val chargeSDK: ChargeSDK): ViewModel() {

    lateinit var navigation: NavController
    private val settings: Settings = Settings()

    private val _quotes = MutableStateFlow<List<Charge>>(emptyList())
    val quotes: StateFlow<List<Charge>> = _quotes.asStateFlow()

    private val _loanId = MutableStateFlow<Long>(-1)
    val loanId: StateFlow<Long> = _loanId.asStateFlow()

    init {
        viewModelScope.launch {
            loadQuotes()
        }
    }

    fun loadQuotes() {
        val loanId = settings.getLong(Constants.LOAN_ID, 0)
        _loanId.value = loanId
        _quotes.value = emptyList()
        val dataQuotes = chargeSDK.selectAllChargeByLoanId(loanId)
        if(dataQuotes.isNotEmpty()) {
            _quotes.value = dataQuotes
        }
    }

    fun onBackClicked() {
        println("Return loans screen")
        navigation.popBackStack()
        navigation.navigate("activeLoans")
    }

    fun updateQuoteList(){
        navigation.popBackStack()
        navigation.navigate("listQuotes")
    }
}