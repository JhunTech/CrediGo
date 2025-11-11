package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.sdk.ClientSDK
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDateTime

class ClientsViewModel (private val sdk: ClientSDK): ViewModel(){

    lateinit var navigation: NavController
    private val settings: Settings = Settings()

    private val _clients = MutableStateFlow(
        listOf(
            Client(1, 0, "", "123456789", "Ethan Carter", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
            Client(2, 0,"", "987654321", "Olivia Bennett", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
            Client(3, 0,"", "555555555", "Noah Thompson", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
            Client(4, 0,"", "111111111", "Ava Harper", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
            Client(5, 0,"", "999999999", "Liam Foster", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524"))
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
        Client(1, 0, "", "123456789", "Ethan Carter", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(2, 0,"", "987654321", "Olivia Bennett", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(3, 0,"", "555555555", "Noah Thompson", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(4, 0,"", "111111111", "Ava Harper", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(5, 0,"", "999999999", "Liam Foster", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524"))
    )

    fun onAddClick() {
        settings.putInt(Constants.CLIENT_ID, -1)
        navigation.navigate("newclient")
    }
}