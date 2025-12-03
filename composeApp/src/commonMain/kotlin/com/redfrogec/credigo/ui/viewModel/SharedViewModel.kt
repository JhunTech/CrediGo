package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel(): ViewModel()  {

    private val _clientSelected = MutableStateFlow<Client?>(null)
    var clientSelected: StateFlow<Client?> = _clientSelected

    private val _externalSearchClient = MutableStateFlow<Boolean>(false)
    val externalSearchClient: StateFlow<Boolean> = _externalSearchClient

    fun onSelectedClient(client: Client){
        _clientSelected.value = client
    }

    fun onExternalSearchClient(externalSearchClient: Boolean) {
        _externalSearchClient.value = externalSearchClient
    }
}