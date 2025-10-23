package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.PreguntaSeguridad
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.redfrogec.credigo.domain.utils.isValidEmail
import com.redfrogec.credigo.domain.utils.isValidPassword
import com.redfrogec.credigo.domain.utils.loadSecurityQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PasswordRecoveryViewModel(private val sdk: UserSDK) : ViewModel() {

    init {
        viewModelScope.launch {
            securityQuestions = loadSecurityQuestions()
        }
    }

    lateinit var navigation: NavController

    lateinit var securityQuestions : List<PreguntaSeguridad>

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _securityQuestion = MutableStateFlow("")
    val securityQuestion: StateFlow<String> = _securityQuestion.asStateFlow()

    private val _idSecurityQuestion = MutableStateFlow(0)
    val idSecurityQuestion: StateFlow<Int> = _idSecurityQuestion.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _resetEnabled = MutableStateFlow(false)
    val resetEnabled: StateFlow<Boolean> = _resetEnabled.asStateFlow()

    fun getQuestions(): List<PreguntaSeguridad> = securityQuestions

    fun onEmailChange(value: String) {
        _email.value = value
        resetPassword()
    }

    fun onSecurityQuestionChange(id: Int, question: String) {
        _idSecurityQuestion.value = id
        _securityQuestion.value = question
        resetPassword()
    }

    fun onAnswerChange(value: String) {
        _answer.value = value
        resetPassword()
    }

    fun onNewPasswordChange(value: String) {
        _newPassword.value = value
        resetPassword()
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
        resetPassword()
    }

    fun resetPassword() {
        _resetEnabled.value = false
        when {
            !isValidEmail(_email.value) -> _errorMessage.value = "Formato incorrecto de email"
            !isValidPassword(_newPassword.value) -> _errorMessage.value = "Formato incorrecto de password"
            !_securityQuestion.value.isNotBlank() -> _errorMessage.value = "Selecciona una pregunta de seguridad"
            !_answer.value.isNotBlank() -> _errorMessage.value = "Ingresa la respuesta de seguridad"
            _confirmPassword.value != _newPassword.value -> _errorMessage.value = "Repetir el password correctamente"
            else -> {
                // Aquí llamarías a tu API o lógica de recuperación de contraseña
                _errorMessage.value = ""
                _resetEnabled.value = true
                println("Password reset successful for ${email.value}")
            }
        }
    }

    fun onBackClicked() {
        println("Retornar a pantalla de Login")
        navigation.popBackStack("login", inclusive = false)
    }
}