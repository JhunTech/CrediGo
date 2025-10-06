package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.ClientStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(navController: NavController) : ViewModel() {

    private val _navigation = navController

    private val _totalLoansGranted = MutableStateFlow(120)
    val totalLoansGranted: StateFlow<Int> = _totalLoansGranted.asStateFlow()

    private val _totalAmountLent = MutableStateFlow("$500,000")
    val totalAmountLent: StateFlow<String> = _totalAmountLent.asStateFlow()

    var clients = mutableStateListOf(
        Client(
            1, "Ethan Carter", "$15,000",
            status = ClientStatus.Active
        ),
        Client(
            2, "Olivia Bennett", "$12,000",
            status = ClientStatus.Active
        ),
        Client(
            3, "Noah Thompson", "$10,000",
            status = ClientStatus.Active
        )
    )
        private set

    fun refreshData() {
        // Aquí iría la lógica para actualizar desde tu API
        println("Refreshing dashboard data...")
    }
}