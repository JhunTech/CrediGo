package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel(navController: NavController) : ViewModel() {

    private val _navigation = navController

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> = _repeatPassword.asStateFlow()

    private val _securityQuestion = MutableStateFlow("")
    val securityQuestion: StateFlow<String> = _securityQuestion.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer.asStateFlow()

    private val _signUpEnabled = MutableStateFlow(false)
    val signUpEnabled: StateFlow<Boolean> = _signUpEnabled.asStateFlow()

    private val questions = listOf(
        "What is your pet's name?",
        "What is your mother's maiden name?",
        "What was your first school?",
        "What is your favorite color?"
    )
    fun getQuestions(): List<String> = questions

    fun onEmailChanged(newValue: String) {
        _email.value = newValue
        validateForm()
    }

    fun onNameChanged(newValue: String) {
        _name.value = newValue
        validateForm()
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
        validateForm()
    }

    fun onRepeatPasswordChanged(newValue: String) {
        _repeatPassword.value = newValue
        validateForm()
    }

    fun onSecurityQuestionChanged(newValue: String) {
        _securityQuestion.value = newValue
        validateForm()
    }

    fun onAnswerChanged(newValue: String) {
        _answer.value = newValue
        validateForm()
    }

    private fun validateForm() {
        _signUpEnabled.value =
            _email.value.isNotBlank() &&
                    _name.value.isNotBlank() &&
                    _password.value.isNotBlank() &&
                    _repeatPassword.value == _password.value &&
                    _securityQuestion.value.isNotBlank() &&
                    _answer.value.isNotBlank()
    }

    fun onSignUpClicked() {
        println("Registrando usuario con email: ${_email.value}")
    }

    fun onSignInClicked() {
        println("Navegar a pantalla de Login")
        _navigation.popBackStack("login", inclusive = false)
    }
}