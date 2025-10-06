package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.ClientStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ClientsViewModel (navController: NavController): ViewModel(){

    private val _navigation = navController

    private val _clients = MutableStateFlow(
        listOf(
            Client(1, "Ethan Carter","", ClientStatus.Active),
            Client(2, "Olivia Bennett", "",ClientStatus.Active),
            Client(3, "Noah Thompson", "",ClientStatus.Blocked),
            Client(4, "Ava Harper", "",ClientStatus.Active),
            Client(5, "Liam Foster", "",ClientStatus.Blocked)
        )
    )
    val clients: StateFlow<List<Client>> = _clients

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _clients.update { list ->
            if (query.isBlank()) defaultClients()
            else defaultClients().filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    private fun defaultClients() = listOf(
        Client(1, "Ethan Carter","", ClientStatus.Active),
        Client(2, "Olivia Bennett","", ClientStatus.Active),
        Client(3, "Noah Thompson","", ClientStatus.Blocked),
        Client(4, "Ava Harper","", ClientStatus.Active),
        Client(5, "Liam Foster","", ClientStatus.Blocked)
    )
}