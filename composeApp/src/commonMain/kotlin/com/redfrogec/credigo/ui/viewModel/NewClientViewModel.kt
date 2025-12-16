package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.sdk.ClientSDK
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import com.redfrogec.credigo.domain.utils.isValidEmail
import com.redfrogec.credigo.domain.utils.isValidIdentification
import com.redfrogec.credigo.domain.utils.isValidName
import com.redfrogec.credigo.domain.utils.isValidPhone
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class NewClientViewModel(private val sdk: ClientSDK, private val sharedViewModel: SharedViewModel): ViewModel() {

    lateinit var navigation: NavController

    private val settings: Settings = Settings()

    private val _clientId= MutableStateFlow(0)
    val clientId: StateFlow<Int> = _clientId.asStateFlow()

    private val _userId= MutableStateFlow(0)
    val userId: StateFlow<Int> = _userId.asStateFlow()

    private val _image = MutableStateFlow("")
    val image: StateFlow<String> = _image.asStateFlow()

    private val _identification= MutableStateFlow("")
    val identification: StateFlow<String> = _identification.asStateFlow()

    private val _name= MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _blocked= MutableStateFlow(false)
    val blocked: StateFlow<Boolean> = _blocked.asStateFlow()

    private val _registerDate = MutableStateFlow("")
    val registerDate: StateFlow<String> = _registerDate.asStateFlow()

    private val _newClientEnabled = MutableStateFlow(false)
    val newClientEnabled: StateFlow<Boolean> = _newClientEnabled.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _clientOk = MutableStateFlow(false)
    val clientOk: StateFlow<Boolean> = _clientOk.asStateFlow()

    private val _clientFail = MutableStateFlow(false)
    val clientFail: StateFlow<Boolean> = _clientFail.asStateFlow()

    private val _clientMessage = MutableStateFlow("")
    val clientMessage: StateFlow<String> = _clientMessage.asStateFlow()

    private var _showLoading = MutableStateFlow(false)
    var showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _clientId.value = settings.getInt(Constants.CLIENT_ID, 0)
            _userId.value = settings.getInt(Constants.USER_ID, 0)
            val dataClient = sdk.selectClientById(_clientId.value.toLong())
            if(dataClient != null)
            {
                loadClient(dataClient)
            }
        }
    }

    fun loadClient(clientData: Client){
        _clientId.value = clientData.id
        _userId.value = clientData.userId
        _image.value = clientData.image
        _identification.value = clientData.identification
        _name.value = clientData.name
        _email.value = clientData.email
        _phone.value = clientData.phone
        _address.value = clientData.address
        _blocked.value = clientData.blocked
        _registerDate.value = clientData.registerDate.toString()
    }

    fun onImageChanged(newValue: String) {
        _image.value = newValue
        validateForm()
    }

    fun onIdentificationChanged(newValue: String) {
        _identification.value = newValue
        validateForm()
    }

    fun onNameChanged(newValue: String) {
        _name.value = newValue
        validateForm()
    }

    fun onEmailChanged(newValue: String) {
        _email.value = newValue
        validateForm()
    }

    fun onPhoneChanged(newValue: String) {
        _phone.value = newValue
        validateForm()
    }

    fun onAddressChanged(newValue: String) {
        _address.value = newValue
        validateForm()
    }

    fun onBlockedChanged(newValue: Boolean) {
        _blocked.value = newValue
        validateForm()
    }

    fun onRegisterDateChanged(newValue: String) {
        _registerDate.value = newValue
        validateForm()
    }

    private fun validateForm() {
        _newClientEnabled.value = false
        when {
            !isValidIdentification(_identification.value) -> _errorMessage.value = "Completa la identificación del cliente"
            !isValidName(_name.value) -> _errorMessage.value = "Formato incorrecto de nombre"
            !isValidEmail(_email.value) -> _errorMessage.value = "Formato incorrecto de email"
            !isValidPhone(_phone.value) -> _errorMessage.value = "Formato incorrecto de teléfono"
            !_address.value.isNotBlank() -> _errorMessage.value = "Ingresa una dirección"
            !_registerDate.value.isNotBlank() -> _errorMessage.value = "No existe fecha de registro del cliente"
            else -> {
                _errorMessage.value = ""
                _newClientEnabled.value = true
            }
        }
    }

    fun onNewClientClicked() {
        _clientOk.value=false
        _clientFail.value=false

        _showLoading.value = true
        if(_clientId.value < 0) {
            val insertClient = sdk.insertClient(
                _userId.value.toLong(),
                _image.value,
                _identification.value,
                _name.value,
                _email.value,
                _phone.value,
                _address.value,
                _blocked.value,
                currentDateDisplay()
            )
            if(insertClient.toInt() > 0) {
                _clientMessage.value = "Registro exitoso del cliente: ${_name.value}"
                println(_clientMessage.value)
                _errorMessage.value = ""
                _clientOk.value=true
                _clientFail.value=false
            }
            else{
                _clientMessage.value = "Error de registro del cliente: ${_name.value}"
                println(_clientMessage.value)
                _errorMessage.value = settings.getString(Constants.ERROR_MESSAGE, "")
                _clientOk.value=false
                _clientFail.value=true
            }
        }
        else
        {
            val updateClient = sdk.updateDataClient(
                _userId.value.toLong(),
                _image.value,
                _identification.value,
                _name.value,
                _email.value,
                _phone.value,
                _address.value,
                _blocked.value,
                LocalDateTime.parse(_registerDate.value),
                _clientId.value.toLong()
            )
            if(updateClient.toInt() > 0) {
                _clientMessage.value = "Actualización del cliente: ${_name.value}"
                println(_clientMessage.value)
                _errorMessage.value = ""
                _clientOk.value=true
                _clientFail.value=false
            }
            else{
                _clientMessage.value = "Error de actualización del cliente: ${_name.value}"
                println(_clientMessage.value)
                _errorMessage.value = settings.getString(Constants.ERROR_MESSAGE, "")
                _clientOk.value=false
                _clientFail.value=true
            }
        }
        _showLoading.value = false
    }

    fun onBackClicked() {
        println("Return clients screen")
        sharedViewModel.onExternalSearch(true)
        navigation.popBackStack()
    }
}