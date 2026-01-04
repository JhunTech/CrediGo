package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.loanUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel(): ViewModel()  {

    private val _clientSelected = MutableStateFlow<Client?>(null)
    var clientSelected: StateFlow<Client?> = _clientSelected

    private val _loanSelected = MutableStateFlow<loanUI?>(null)
    var loanSelected: StateFlow<loanUI?> = _loanSelected

    private val _externalSearch = MutableStateFlow<Boolean>(false)
    val externalSearch: StateFlow<Boolean> = _externalSearch

    fun onSelectedClient(client: Client){
        _clientSelected.value = client
    }

    fun onExternalSearch(newValue: Boolean){
        _externalSearch.value = newValue
    }

    fun onSelectedLoan(loan: loanUI){
        _loanSelected.value = loan
    }
}