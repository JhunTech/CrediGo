package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.ChargeType
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.SecurityQuestion
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import com.redfrogec.credigo.domain.utils.isValidEmail
import com.redfrogec.credigo.domain.utils.isValidPassword
import com.redfrogec.credigo.domain.utils.loadSecurityQuestions
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val sdk: UserSDK) : ViewModel() {

    init {
        viewModelScope.launch {
            securityQuestions = loadSecurityQuestions()
        }
    }
    /*val securityQuestions : List<PreguntaSeguridad> = runBlocking {
        cargarPreguntaSeguridad()
    }*/

    lateinit var navigation: NavController

    lateinit var securityQuestions : List<SecurityQuestion>

    private val settings: Settings = Settings()

    private var _showLoading = MutableStateFlow(false)
    var showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

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

    private val _idSecurityQuestion = MutableStateFlow(0L)
    val idSecurityQuestion: StateFlow<Long> = _idSecurityQuestion.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer.asStateFlow()

    private val _signUpEnabled = MutableStateFlow(false)
    val signUpEnabled: StateFlow<Boolean> = _signUpEnabled.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _signUpOk = MutableStateFlow(false)
    val signUpOk: StateFlow<Boolean> = _signUpOk.asStateFlow()

    private val _signUpFail = MutableStateFlow(false)
    val signUpFail: StateFlow<Boolean> = _signUpFail.asStateFlow()

    fun getQuestions(): List<SecurityQuestion> = securityQuestions

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

    fun onSecurityQuestionChanged(id: Long, question: String) {
        _idSecurityQuestion.value = id
        _securityQuestion.value = question
        validateForm()
    }

    fun onAnswerChanged(newValue: String) {
        _answer.value = newValue
        validateForm()
    }

    private fun validateForm() {
        _signUpEnabled.value = false
        when {
            !isValidEmail(_email.value) -> _errorMessage.value = "Formato incorrecto de email"
            !isValidPassword(_password.value) -> _errorMessage.value = "Formato incorrecto de password"
            !_name.value.isNotBlank() -> _errorMessage.value = "Ingresar un nombre valido"
            _repeatPassword.value != _password.value -> _errorMessage.value = "Repetir el password correctamente"
            !_securityQuestion.value.isNotBlank() -> _errorMessage.value = "Selecciona una pregunta de seguridad"
            !_answer.value.isNotBlank() -> _errorMessage.value = "Ingresa la respuesta de seguridad"
            else -> {
                _errorMessage.value = ""
                _signUpEnabled.value = true
            }
        }
    }

    fun onSignUpClicked() {
        _signUpOk.value=false
        _signUpFail.value=false

        _showLoading.value = true
        val insertUser = sdk.insertUser(
            "",
            _email.value,
            _name.value,
            _password.value,
            _idSecurityQuestion.value.toLong(),
            _answer.value,
            currentDateDisplay(),
            currentDateDisplay(),
            0,
            "",
            currentDateDisplay())
        _showLoading.value = false

        if(insertUser.toInt() > 0) {
            println("Registrando usuario con email: ${_email.value}")
            _errorMessage.value = ""
            _signUpOk.value=true
            _signUpFail.value=false
        }
        else{
            println("Error de registro de usuario con email: ${_email.value}")
            _errorMessage.value = settings.getString(Constants.ERROR_MESSAGE, "")
            _signUpOk.value=false
            _signUpFail.value=true
        }
    }

    fun onSignInClicked() {
        println("Navegar a pantalla de Login")
        navigation.popBackStack("login", inclusive = false)
    }

    fun goDashBoard()
    {
        navigation.navigate("dashboard")
    }
}