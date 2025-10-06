package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PasswordRecoveryViewModel(navController: NavController) : ViewModel() {

    private val _navigation = navController

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _securityQuestion = MutableStateFlow("")
    val securityQuestion: StateFlow<String> = _securityQuestion.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val questions = listOf(
        "What is your pet's name?",
        "What is your mother's maiden name?",
        "What was your first school?",
        "What is your favorite color?"
    )
    fun getQuestions(): List<String> = questions

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onSecurityQuestionChange(value: String) {
        _securityQuestion.value = value
    }

    fun onAnswerChange(value: String) {
        _answer.value = value
    }

    fun onNewPasswordChange(value: String) {
        _newPassword.value = value
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
    }

    fun resetPassword() {
        when {
            _email.value.isBlank() -> _errorMessage.value = "Email cannot be empty"
            _securityQuestion.value.isBlank() -> _errorMessage.value = "Select a security question"
            _answer.value.isBlank() -> _errorMessage.value = "Answer cannot be empty"
            _newPassword.value.length < 6 -> _errorMessage.value = "Password must be at least 6 characters"
            _newPassword.value != confirmPassword.value -> _errorMessage.value = "Passwords do not match"
            else -> {
                // Aquí llamarías a tu API o lógica de recuperación de contraseña
                _errorMessage.value = ""
                println("Password reset successful for ${email.value}")
            }
        }
    }
}