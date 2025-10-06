package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.redfrogec.credigo.data.model.ProfileUiState
import androidx.compose.runtime.State
import androidx.navigation.NavController

class ProfileViewModel(navController: NavController) : ViewModel() {

    private val _navigation = navController
    private val _uiState = mutableStateOf(ProfileUiState())
    val uiState: State<ProfileUiState> get() = _uiState /** Cargar datos iniciales (puede ser llamada desde el platform entry point). */

    fun loadInitialData() {
        _uiState.value = ProfileUiState( name = "Sophia Carter", memberSince = "Member since 2021", profileImageUrl = null )
    }

    /** Actualizaciones sencillas sobre el estado */
    fun updateName(newName: String) {
        _uiState.value = _uiState.value.copy(name = newName)
    }

    fun updateMemberSince(text: String) {
        _uiState.value = _uiState.value.copy(memberSince = text)
    }

    fun updateProfileImage(url: String?) {
        _uiState.value = _uiState.value.copy(profileImageUrl = url)
    }

    /** * Ejemplo de método que en una implementación real llamaría a un repositorio/API. * En commonMain puedes exponer la firma y hacer la implementación por plataforma si lo necesitas. */
    suspend fun refreshFromRepository(fetcher: suspend () -> ProfileUiState) {
        val remote = fetcher()
        _uiState.value = remote
    }
}
