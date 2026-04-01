package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.ProfileUiState
import com.redfrogec.credigo.data.model.SecurityQuestion
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.redfrogec.credigo.domain.utils.loadChargeTypes
import com.redfrogec.credigo.domain.utils.loadInterests
import com.redfrogec.credigo.domain.utils.loadLoanTypes
import com.redfrogec.credigo.domain.utils.loadPaymentTypes
import com.redfrogec.credigo.domain.utils.loadSecurityQuestions
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val sdk: UserSDK) : ViewModel() {

    lateinit var navigation: NavController
    private val settings: Settings = Settings()
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState
    lateinit var securityQuestions : List<SecurityQuestion>

    private val _deleteUserOk = MutableStateFlow(false)
    val deleteUserOk: StateFlow<Boolean> = _deleteUserOk.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _userFail = MutableStateFlow(false)
    val userFail: StateFlow<Boolean> = _userFail.asStateFlow()

    private val _showConfirmDelete = MutableStateFlow(false)
    val showConfirmDelete: StateFlow<Boolean> = _showConfirmDelete.asStateFlow()

    init {
        viewModelScope.launch {
            securityQuestions = loadSecurityQuestions()
            loadInitialData()
        }
    }

    fun onChangePassword() {
        println("Change password clicked")
    }

    fun onLogout() {
        println("Logout clicked")
    }

    fun onChangePhoto() {
        println("Change profile photo")
    }

    fun showConfirmDelete() {
        _showConfirmDelete.value = true
    }

    fun onDeleteUser() {
        _showConfirmDelete.value = false
        _userFail.value = false
        _deleteUserOk.value = false
        _errorMessage.value = ""
        val userId = settings.getLong(Constants.USER_ID, 0)
        if (userId != 0L) {
            val deleteUserData = sdk.deleteUserData(userId)
            if (deleteUserData != 0L) {
                settings.remove(Constants.USER_ID)
                _deleteUserOk.value = true
                println("Datos del usuario $userId borrados.")
            }
            else
            {
                _userFail.value = true
                _errorMessage.value = "No se pudo borrar los datos del usuario."
                println("No se pudo borrar los datos del usuario: $userId")
            }
        }
    }

    fun loadInitialData() {
        val userId = settings.getLong(Constants.USER_ID, 0)
        if (userId != 0L) {
            val userData = sdk.selectDataUser(userId)
            if (userData != null) {
                val securityQuestion = securityQuestions.find { it.id == userData.questionId }?.description
                _uiState.value = ProfileUiState( displayName = userData.name, fullName = userData.name, email = userData.email, securityQuestion = securityQuestion.toString(), responseQuestion = userData.response, clientId = userData.id.toString(), phone = "XXXX-XXXX-XXXX", officialId = userData.id.toString(), isVerified = true)
            }
        }
    }

    /** * Ejemplo de método que en una implementación real llamaría a un repositorio/API. * En commonMain puedes exponer la firma y hacer la implementación por plataforma si lo necesitas. */
    /*suspend fun refreshFromRepository(fetcher: suspend () -> ProfileUiState) {
        val remote = fetcher()
        _uiState.value = remote
    }*/
}
