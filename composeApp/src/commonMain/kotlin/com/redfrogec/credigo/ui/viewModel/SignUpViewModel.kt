package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.PreguntaSeguridad
import com.redfrogec.credigo.domain.controls.LoadingPopup
import com.redfrogec.credigo.domain.utils.cargarPreguntaSeguridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SignUpViewModel(navController: NavController) : ViewModel() {

    init {
        viewModelScope.launch {
            securityQuestions = cargarPreguntaSeguridad()
        }
    }
    /*val securityQuestions : List<PreguntaSeguridad> = runBlocking {
        cargarPreguntaSeguridad()
    }*/

    lateinit var securityQuestions : List<PreguntaSeguridad>

    private var _showLoading = MutableStateFlow(false)
    var showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

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

    private val _idSecurityQuestion = MutableStateFlow(0)
    val idSecurityQuestion: StateFlow<Int> = _idSecurityQuestion.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer.asStateFlow()

    private val _signUpEnabled = MutableStateFlow(false)
    val signUpEnabled: StateFlow<Boolean> = _signUpEnabled.asStateFlow()

    fun getQuestions(): List<PreguntaSeguridad> = securityQuestions

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

    fun onSecurityQuestionChanged(id: Int, question: String) {
        _idSecurityQuestion.value = id
        _securityQuestion.value = question
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