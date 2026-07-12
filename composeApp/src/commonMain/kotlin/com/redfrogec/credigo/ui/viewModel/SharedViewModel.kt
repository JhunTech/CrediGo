package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.LoanUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel(): ViewModel()  {

    private val _clientSelected = MutableStateFlow<Client?>(null)
    var clientSelected: StateFlow<Client?> = _clientSelected

    private val _loanSelected = MutableStateFlow<LoanUI?>(null)
    var loanSelected: StateFlow<LoanUI?> = _loanSelected

    private val _externalSearch = MutableStateFlow<Boolean>(false)
    val externalSearch: StateFlow<Boolean> = _externalSearch

    fun onSelectedClient(client: Client){
        _clientSelected.value = client
    }

    fun onExternalSearch(newValue: Boolean){
        _externalSearch.value = newValue
    }

    fun onSelectedLoan(loan: LoanUI){
        _loanSelected.value = loan
    }
}