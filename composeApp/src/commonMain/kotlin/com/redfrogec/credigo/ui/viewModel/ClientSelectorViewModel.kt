package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ClientSelectorViewModel (initial: List<Client> = emptyList()): ViewModel() {
    private val _clients = MutableStateFlow(initial)
    val clients: StateFlow<List<Client>> = _clients

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _dialogOpen = MutableStateFlow(false)
    val dialogOpen: StateFlow<Boolean> = _dialogOpen

    private val _selectedClient = MutableStateFlow<Client?>(null)
    val selectedClient: StateFlow<Client?> = _selectedClient

    fun setClients(list: List<Client>) { _clients.value = list }

    fun openDialog() { _dialogOpen.value = true }
    fun closeDialog() { _dialogOpen.value = false }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun selectClient(client: Client) {
        _selectedClient.value = client
        _dialogOpen.value = false
    }

    fun filteredClients(): List<Client> {
        val query = _searchQuery.value.trim()
        return if (query.isEmpty()) _clients.value
        else _clients.value.filter { it.name.contains(query, ignoreCase = true) }
    }
}