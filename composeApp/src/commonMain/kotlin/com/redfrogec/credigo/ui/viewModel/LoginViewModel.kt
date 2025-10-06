package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
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

    fun onUsernameChanged(newValue: String) {
        _username.value = newValue
        validateLogin()
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
        validateLogin()
    }

    private fun validateLogin() {
        _loginEnabled.value = _username.value.isNotBlank() && _password.value.isNotBlank()
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