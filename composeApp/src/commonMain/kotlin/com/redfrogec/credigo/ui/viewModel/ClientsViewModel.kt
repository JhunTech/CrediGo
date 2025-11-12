package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.sdk.ClientSDK
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlin.collections.emptyList

class ClientsViewModel (private val sdk: ClientSDK): ViewModel(){

    lateinit var navigation: NavController
    private val settings: Settings = Settings()

    private val _showConfirmDelete = MutableStateFlow(false)
    val showConfirmDelete: StateFlow<Boolean> = _showConfirmDelete.asStateFlow()

    private val _selectedIdClient = MutableStateFlow(-1)
    val selectedIdClient: StateFlow<Int> = _selectedIdClient.asStateFlow()

    private val _defaultClients = MutableStateFlow<List<Client>>(emptyList())
    val defaultClients: StateFlow<List<Client>?> = _defaultClients.asStateFlow()

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>?> = _clients.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _clientDeleteOk = MutableStateFlow(false)
    val clientDeleteOk: StateFlow<Boolean> = _clientDeleteOk.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        viewModelScope.launch {
            loadClients()
        }
    }

    fun loadClients(){
        val userId = settings.getInt(Constants.USER_ID, 0)
        val dataClients = sdk.selectAllClients(userId.toLong())
        if(dataClients != null) {
            _defaultClients.value = dataClients
            _clients.value = dataClients
        }
    }

    fun updateClientList(){
        navigation.popBackStack()
        navigation.navigate("clients")
    }

    fun onConfirmDeleteChanged(newValue: Boolean, id: Int) {
        _showConfirmDelete.value = newValue
        _selectedIdClient.value = id
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _clients.update { list ->
            if (query.isBlank()) _defaultClients.value
            else _defaultClients.value.filter { it.name.contains(query, ignoreCase = true) }
        }
    }

    fun onRefreshChanged(newValue: Boolean){
        _isRefreshing.value = newValue
    }

    fun onAddClick() {
        settings.putInt(Constants.CLIENT_ID, -1)
        navigation.navigate("newclient")
    }

    fun deleteClient(id: Int) {
        val deleteClient = sdk.deleteClient(id.toLong())
        if(deleteClient.toInt() > 0) {
            print("Cliente borrado correctamente con ID: $id")
        }
        else{
            print("Error al borrar el cliente con el ID: $id")
        }
        loadClients()
    }

    fun updateClient(id: Int) {
        settings.putInt(Constants.CLIENT_ID, id)
        navigation.navigate("newclient")
    }
}