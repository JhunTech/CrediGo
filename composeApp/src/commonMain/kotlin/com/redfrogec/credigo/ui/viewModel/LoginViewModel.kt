package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.domain.utils.isValidEmail
import com.redfrogec.credigo.domain.utils.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(navController: NavController) : ViewModel() {

    private val _navigation = navController

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginEnabled = MutableStateFlow(false)
    val loginEnabled: StateFlow<Boolean> = _loginEnabled.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onUsernameChanged(newValue: String) {
        _username.value = newValue
        validateLogin()
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
        validateLogin()
    }

    private fun validateLogin() {
        _loginEnabled.value = false
        when {
            !isValidEmail(_username.value) -> _errorMessage.value = "Invalid email"
            !isValidPassword(_password.value) -> _errorMessage.value = "Invalid password"
            else -> {

                _loginEnabled.value = true
                _errorMessage.value = ""
                println("Login successful for ${username.value}")
            }
        }
    }

    fun onLoginClicked() {
        // Aquí iría la lógica para llamar al backend (API REST)
        _navigation.navigate("dashboard")
    }

    fun onForgotPasswordClicked() {
        _navigation.navigate("passwordrecovery")
    }

    fun onSignUpClicked() {
        _navigation.navigate("signup")
    }
}