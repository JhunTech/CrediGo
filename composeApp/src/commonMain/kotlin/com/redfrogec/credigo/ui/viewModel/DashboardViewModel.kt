package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.domain.utils.CurrentDateDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel(): ViewModel()  {
    val client = Client(
        id = -1,
        userId = -1,
        image = "",
        identification = "123456789",
        name = "John Doe",
        email = "william.henry.harrison@example-pet-store.com",
        phone = "123-456-7890",
        address = "123 Main St",
        blocked = false,
        registerDate = CurrentDateDisplay()
    )
    private val _clientData = MutableStateFlow(client)
    val clientData: StateFlow<Client> = _clientData.asStateFlow()
}