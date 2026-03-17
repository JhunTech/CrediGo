package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.ProfileUiState
import androidx.compose.runtime.State
import androidx.navigation.NavController
import com.redfrogec.credigo.domain.sdk.UserSDK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(private val sdk: UserSDK) : ViewModel() {

    lateinit var navigation: NavController
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState
    fun onChangePassword() {
        println("Change password clicked")
    }

    fun onLogout() {
        println("Logout clicked")
    }

    fun onDeleteUser() {
        println("Delete user clicked")
    }

    fun onChangePhoto() {
        println("Change profile photo")
    }

    /*fun loadInitialData() {
        //_uiState.value = ProfileUiState( name = "Sophia Carter", memberSince = "Member since 2021", profileImageUrl = null )
    }

    /** * Ejemplo de método que en una implementación real llamaría a un repositorio/API. * En commonMain puedes exponer la firma y hacer la implementación por plataforma si lo necesitas. */
    suspend fun refreshFromRepository(fetcher: suspend () -> ProfileUiState) {
        val remote = fetcher()
        _uiState.value = remote
    }*/
}
