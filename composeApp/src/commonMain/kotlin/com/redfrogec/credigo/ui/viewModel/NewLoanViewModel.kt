package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.data.model.ChargeType
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.Interest
import com.redfrogec.credigo.data.model.LoanType
import com.redfrogec.credigo.data.model.NumberCharge
import com.redfrogec.credigo.data.model.PaymentType
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.redfrogec.credigo.domain.sdk.ClientSDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import com.redfrogec.credigo.domain.utils.loadChargeTypes
import com.redfrogec.credigo.domain.utils.loadInterests
import com.redfrogec.credigo.domain.utils.loadLoanTypes
import com.redfrogec.credigo.domain.utils.loadPaymentTypes
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus

class NewLoanViewModel(private val loanSDK: LoanSDK, private val clientSDK: ClientSDK, private val chargeSDK: ChargeSDK, private val sharedViewModel: SharedViewModel): ViewModel() {

    lateinit var navigation: NavController
    lateinit var interestList : List<Interest>
    lateinit var loanTypes : List<LoanType>
    lateinit var paymentTypes : List<PaymentType>
    lateinit var chargeTypes : List<ChargeType>
    private val settings: Settings = Settings()

    val quoteNumbersDisplay = MutableList(Constants.MAX_QUOTE) { index ->
        val number = index + 1
        NumberCharge(
            number = number,
            description = "$number cuotas"
        )
    }

    private val _loanId= MutableStateFlow(0L)
    val loanId: StateFlow<Long> = _loanId.asStateFlow()

    private val _loanValue = MutableStateFlow(0.0)
    val loanValue: StateFlow<Double> = _loanValue

    private val _selectedClientId = MutableStateFlow(0L)
    val selectedClientId: StateFlow<Long> = _selectedClientId.asStateFlow()

    private val _selectedClientName = MutableStateFlow("")
    val selectedClientName: StateFlow<String> = _selectedClientName.asStateFlow()

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>?> = _clients.asStateFlow()

    private val _interestId = MutableStateFlow(0)
    val interestId: StateFlow<Int> = _interestId.asStateFlow()

    private val _interestRate = MutableStateFlow(0.0)
    val interestRate: StateFlow<Double> = _interestRate.asStateFlow()

    private val _interestDescription = MutableStateFlow("")
    val interestDescription: StateFlow<String> = _interestDescription.asStateFlow()

    private val _loanType = MutableStateFlow("")
    val loanType: StateFlow<String> = _loanType.asStateFlow()

    private val _loanTypeId = MutableStateFlow(0)
    val loanTypeId: StateFlow<Int> = _loanTypeId.asStateFlow()

    private val _loanTypeGraceQuotes = MutableStateFlow(0)
    val loanTypeGraceQuotes: StateFlow<Int> = _loanTypeGraceQuotes.asStateFlow()

    private val _quotes = MutableStateFlow(1)
    val quotes: StateFlow<Int> = _quotes.asStateFlow()

    private val _quotesDescription = MutableStateFlow("")
    val quotesDescription: StateFlow<String> = _quotesDescription.asStateFlow()

    private val _totalQuotes = MutableStateFlow(0)
    val totalQuotes: StateFlow<Int> = _totalQuotes.asStateFlow()

    private val _paymentTypeId = MutableStateFlow(0)
    val paymentTypeId: StateFlow<Int> = _paymentTypeId.asStateFlow()

    private val _paymentTypeDescription = MutableStateFlow("")
    val paymentTypeDescription: StateFlow<String> = _paymentTypeDescription.asStateFlow()

    private val _paymentTypeDays = MutableStateFlow(0)
    val paymentTypeDays: StateFlow<Int> = _paymentTypeDays.asStateFlow()

    private val _generatedPlan = MutableStateFlow<List<Charge>>(emptyList())
    val generatedPlan: StateFlow<List<Charge>> = _generatedPlan

    private val _generatePlanEnabled = MutableStateFlow(false)
    val generatePlanEnabled: StateFlow<Boolean> = _generatePlanEnabled.asStateFlow()

    private val _registerLoanEnabled = MutableStateFlow(false)
    val registerLoanEnabled: StateFlow<Boolean> = _registerLoanEnabled.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _showConfirmRegister = MutableStateFlow(false)
    val showConfirmRegister: StateFlow<Boolean> = _showConfirmRegister.asStateFlow()

    private val _registerDate = MutableStateFlow("")
    val registerDate: StateFlow<String> = _registerDate.asStateFlow()

    private val _loanOk = MutableStateFlow(false)
    val loanOk: StateFlow<Boolean> = _loanOk.asStateFlow()

    private val _loanFail = MutableStateFlow(false)
    val loanFail: StateFlow<Boolean> = _loanFail.asStateFlow()

    private var _showLoading = MutableStateFlow(false)
    var showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

    fun loadClients(){
        val userId = settings.getLong(Constants.USER_ID, 0)
        val dataClients = clientSDK.selectActiveClients(userId)
        if(dataClients != null) {
            _clients.value = dataClients
        }
    }

    init {
        viewModelScope.launch {
            _loanId.value = settings.getLong(Constants.LOAN_ID, 0L)
            interestList = loadInterests()
            loanTypes = loadLoanTypes()
            paymentTypes = loadPaymentTypes()
            chargeTypes = loadChargeTypes()
            loadClients()
        }
    }

    fun onLoanValueChange(value: Double) {
        _loanValue.value = value
        validateHeaderForm()
    }

    fun onClientSelected(clientId: Long, clientName: String): String {
        _selectedClientId.value = clientId
        _selectedClientName.value = clientName
        validateHeaderForm()

        return _selectedClientName.value
    }

    fun onInterestChange(interest: Interest) {
        _interestId.value = interest.id
        _interestDescription.value = interest.description
        _interestRate.value = interest.value
        validateHeaderForm()
    }

    fun onNumberQuoteSelected(quote: NumberCharge) {
        _quotes.value = quote.number
        _quotesDescription.value = quote.description
        validateHeaderForm()
    }

    fun onLoanTypeSelected(loanType: LoanType)
    {
        _loanTypeId.value = loanType.id
        _loanType.value = loanType.description
        _loanTypeGraceQuotes.value = loanType.graceQuotes
        validateHeaderForm()
    }

    fun onPaymentTypeSelected(paymentType: PaymentType)
    {
        _paymentTypeId.value = paymentType.id
        _paymentTypeDescription.value = paymentType.description
        _paymentTypeDays.value = paymentType.days
        validateHeaderForm()
    }

    fun onRegisterDateChanged(newValue: String) {
        _registerDate.value = newValue
        validateHeaderForm()
    }

    fun onShowConfirmRegister(newValue: Boolean) {
        _showConfirmRegister.value = newValue
    }


    private fun validateHeaderForm() {
        _generatePlanEnabled.value = false

        when {
            _loanValue.value <= 0 -> _errorMessage.value += "El valor del préstamo debe ser mayor a 0\n"
            _selectedClientId.value <= 0 -> _errorMessage.value += "Selecciona un cliente valido\n"
            _interestId.value <= 0 -> _errorMessage.value += "No se ha seleccionado el interes\n"
            _loanTypeId.value <= 0 -> _errorMessage.value += "Selecciona un tipo de préstamo\n"
            _paymentTypeId.value <= 0 -> _errorMessage.value += "Selecciona un tipo de cobro\n"
            _quotes.value <= 0 -> _errorMessage.value += "Selecciona una cantidad de cuotas\n"
            !_registerDate.value.isNotBlank() -> _errorMessage.value += "No existe fecha de registro del préstamo\n"
            else -> {
                _generatePlanEnabled.value = true
            }
        }
    }

    fun generatePlan() {
        val principal = loanValue.value
        val rateDecimal = interestRate.value / 100.0
        val totalQuotesGrace = loanTypeGraceQuotes.value
        val totalQuotesNormal = quotes.value
        val days = _paymentTypeDays.value
        _totalQuotes.value = totalQuotesGrace + totalQuotesNormal
        val interestQuote = principal * rateDecimal
        val quoteValue = (principal/totalQuotesNormal) + interestQuote

        val today = currentDateDisplay()
        var paymentDate = LocalDateTime.parse(registerDate.value).date
        paymentDate = paymentDate.plus(DatePeriod(days = days))
        val newPlan = MutableStateFlow<List<Charge>>(emptyList())
        _showConfirmRegister.value = false
        _registerLoanEnabled.value = false

        for(i in 1..totalQuotes.value) {
            if(totalQuotesGrace > 0 && i <= totalQuotesGrace) {
                newPlan.update { it +
                        Charge(
                            id = -1,
                            loanId = _loanId.value,
                            quotaNumber = i,
                            chargeDate = paymentDate.toString(),
                            customerPaymentDate = null,
                            quotaValue = interestQuote,
                            chargeValue = 0.0,
                            remainingValue = interestQuote,
                            chargeTypeId = 2
                        )
                }
            }
            else{
                newPlan.update { it +
                    Charge(
                        id = -1,
                        loanId = _loanId.value,
                        quotaNumber = i,
                        chargeDate = paymentDate.toString(),
                        customerPaymentDate = null,
                        quotaValue = quoteValue,
                        chargeValue = 0.0,
                        remainingValue = quoteValue,
                        chargeTypeId = 1
                    )
                }
            }
            paymentDate = paymentDate.plus(DatePeriod(days = days))
        }

        if(newPlan.value.isNotEmpty()) {
            _generatedPlan.value = newPlan.value
            _registerLoanEnabled.value = true
        }
    }

    fun showConfirmQuestion() {
        _showConfirmRegister.value = true
        println("Show confirm Question")
    }

    fun onBackClicked() {
        println("Return loans screen")
        navigation.popBackStack()
        navigation.navigate("activeLoans")
    }

    fun showSelectClient(){
        settings.putBoolean(Constants.EXTERNAL_SEARCH_CLIENT, true)
        navigation.navigate("clients")
    }

    fun onNewRegisterLoan(){
        _loanOk.value=false
        _loanFail.value=false
        _showLoading.value = true
        _errorMessage.value = ""

        val insertLoan = loanSDK.insertLoan(
            _selectedClientId.value,
            _loanValue.value,
            _paymentTypeId.value.toLong(),
            _interestId.value.toLong(),
            totalQuotes.value.toLong(),
            true,
            _registerDate.value,
            _registerDate.value,
            _loanTypeId.value.toLong()
        )
        if(insertLoan.toInt() > 0) {
            _errorMessage.value += "Registro del préstamo de $${_loanValue.value}, asignado al cliente: ${_selectedClientName.value}\n"
            println(_errorMessage.value)
            var charTypeNormal: Int = 0
            var charTypeInterest: Int = 0
            val lastInsertId = chargeSDK.lastInsertId()
            for (charge in generatedPlan.value) {
                val insertCharge = chargeSDK.insertCharge(
                    lastInsertId,
                    charge.quotaNumber.toLong(),
                    charge.chargeDate,
                    null,
                    charge.quotaValue,
                    charge.chargeValue,
                    charge.remainingValue,
                    charge.chargeTypeId.toLong()
                )
                if(insertCharge > 0){
                    if(charge.chargeTypeId == 1)
                        charTypeNormal++
                    else
                        charTypeInterest++

                }
            }
            if(charTypeInterest > 0){
                _errorMessage.value += "Con $charTypeInterest cuotas de solo interés\n"
            }
            if(charTypeNormal > 0 && charTypeInterest == 0){
                _errorMessage.value += "y $charTypeNormal cuotas normales con capital + interés\n"
            }
            if(charTypeNormal > 0){
                _errorMessage.value += "Con $charTypeNormal cuotas normales con capital + interés\n"
            }
            println(_errorMessage.value)
            _showLoading.value = false
            _loanOk.value=true
            _loanFail.value=false
        }
        else{
            _errorMessage.value += "Error en el registro de préstamo de $${_loanValue.value}, asignado al cliente: ${_selectedClientName.value}\n"
            println(_errorMessage.value )
            _showLoading.value = false
            _loanOk.value=false
            _loanFail.value=true
        }
    }
}