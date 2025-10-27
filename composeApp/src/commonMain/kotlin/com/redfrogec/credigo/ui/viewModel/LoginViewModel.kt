package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.redfrogec.credigo.domain.utils.isValidEmail
import com.redfrogec.credigo.domain.utils.isValidPassword
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val sdk: UserSDK) : ViewModel() {

    lateinit var navigation: NavController

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _loginEnabled = MutableStateFlow(false)
    val loginEnabled: StateFlow<Boolean> = _loginEnabled.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginFail = MutableStateFlow(false)
    val loginFail: StateFlow<Boolean> = _loginFail.asStateFlow()

    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

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
            !isValidEmail(_username.value) -> _errorMessage.value = "Formato incorrecto de email"
            !isValidPassword(_password.value) -> _errorMessage.value = "Formato incorrecto de password"
            else -> {
                _loginEnabled.value = true
                _errorMessage.value = ""
            }
        }
    }

    fun onLoginClicked() {
        // Aquí iría la lógica para llamar al backend (API REST)
        _showLoading.value = true
        val user = sdk.findUserByEmailAndPassword(_username.value, _password.value)
        _showLoading.value = false
        clearControls()

        if(user != null)
        {
            println("Login successful for ${username.value}")
            navigation.navigate("dashboard")
        }
        else
        {
            println("Login failed for ${username.value}")
            _loginFail.value=true
        }
    }

    fun onForgotPasswordClicked() {
        navigation.navigate("passwordrecovery")
    }

    fun onSignUpClicked() {
        navigation.navigate("signup")
    }

    fun clearControls(){
        _username.value = ""
        _password.value = ""
        _errorMessage.value = ""
        _loginEnabled.value = false
        _loginFail.value=false
    }
}