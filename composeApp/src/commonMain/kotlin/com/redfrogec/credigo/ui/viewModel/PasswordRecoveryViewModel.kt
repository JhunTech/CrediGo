package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.SecurityQuestion
import com.redfrogec.credigo.domain.sdk.UserSDK
import com.redfrogec.credigo.domain.utils.isValidEmail
import com.redfrogec.credigo.domain.utils.isValidPassword
import com.redfrogec.credigo.domain.utils.loadSecurityQuestions
import com.russhwolf.settings.Settings
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

    lateinit var securityQuestions : List<SecurityQuestion>

    private val settings: Settings = Settings()

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

    private var _showLoading = MutableStateFlow(false)
    var showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

    private var _recoveryFail = MutableStateFlow(false)
    var recoveryFail: StateFlow<Boolean> = _recoveryFail.asStateFlow()

    private var _recoveryOk = MutableStateFlow(false)
    var recoveryOk: StateFlow<Boolean> = _recoveryOk.asStateFlow()

    fun getQuestions(): List<SecurityQuestion> = securityQuestions

    fun onEmailChange(value: String) {
        _email.value = value
        validateForm()
    }

    fun onSecurityQuestionChange(id: Int, question: String) {
        _idSecurityQuestion.value = id
        _securityQuestion.value = question
        validateForm()
    }

    fun onAnswerChange(value: String) {
        _answer.value = value
        validateForm()
    }

    fun onNewPasswordChange(value: String) {
        _newPassword.value = value
        validateForm()
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
        validateForm()
    }

    fun validateForm(){
        _resetEnabled.value = false
        when {
            !isValidEmail(_email.value) -> _errorMessage.value = "Formato incorrecto de email"
            !isValidPassword(_newPassword.value) -> _errorMessage.value = "Formato incorrecto de password"
            !_securityQuestion.value.isNotBlank() -> _errorMessage.value = "Selecciona una pregunta de seguridad"
            !_answer.value.isNotBlank() -> _errorMessage.value = "Ingresa la respuesta de seguridad"
            _confirmPassword.value != _newPassword.value -> _errorMessage.value = "Repetir el password correctamente"
            else -> {
                _errorMessage.value = ""
                _resetEnabled.value = true
            }
        }
    }

    fun onBackClicked() {
        println("Return login screen")
        navigation.popBackStack("login", inclusive = false)
    }

    fun resetPasswordClicked(){
        _recoveryFail.value=false
        _recoveryOk.value=false

        _showLoading.value = true
        val recoveryPassword = sdk.recoveryPasswordUserByEmailAndQuestion(
            _newPassword.value,
            _email.value,
            _idSecurityQuestion.value.toLong(),
            _answer.value
        )
        _showLoading.value = false

        if(recoveryPassword.toInt() > 0){
            println("Password reset successful for ${email.value}")
            _errorMessage.value = ""
            _recoveryFail.value=false
            _recoveryOk.value=true
        }
        else{
            println("Password reset error for ${email.value}")
            _errorMessage.value = settings.getString(Constants.ERROR_MESSAGE, "")
            _recoveryFail.value=true
            _recoveryOk.value=false
        }
    }
}