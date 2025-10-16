package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.ClientStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDateTime

class HomeViewModel(navController: NavController) : ViewModel() {

    private val _navigation = navController

    private val _totalLoansGranted = MutableStateFlow(120)
    val totalLoansGranted: StateFlow<Int> = _totalLoansGranted.asStateFlow()

    private val _totalAmountLent = MutableStateFlow("$500,000")
    val totalAmountLent: StateFlow<String> = _totalAmountLent.asStateFlow()

    var clients = mutableStateListOf(
        Client(1, 0, "", "123456789", "Ethan Carter", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(2, 0,"", "987654321", "Olivia Bennett", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(3, 0,"", "555555555", "Noah Thompson", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(4, 0,"", "111111111", "Ava Harper", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524")),
        Client(5, 0,"", "999999999", "Liam Foster", "", "", "", false, LocalDateTime.parse("2025-10-25T12:03:04.524"))
    )
        private set

    fun refreshData() {
        // Aquí iría la lógica para actualizar desde tu API
        println("Refreshing dashboard data...")
    }
}