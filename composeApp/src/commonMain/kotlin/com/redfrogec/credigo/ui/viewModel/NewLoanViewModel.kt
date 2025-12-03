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
import com.redfrogec.credigo.domain.sdk.ClientSDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.redfrogec.credigo.domain.utils.CurrentDateDisplay
import com.redfrogec.credigo.domain.utils.loadChargeTypes
import com.redfrogec.credigo.domain.utils.loadInterests
import com.redfrogec.credigo.domain.utils.loadLoanTypes
import com.redfrogec.credigo.domain.utils.loadPaymentTypes
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class NewLoanViewModel(private val loanSDK: LoanSDK, private val clientSDK: ClientSDK, private val sharedViewModel: SharedViewModel): ViewModel() {

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

    private val _loanId= MutableStateFlow(0)
    val loanId: StateFlow<Int> = _loanId.asStateFlow()

    private val _loanValue = MutableStateFlow(0.00)
    val loanValue: StateFlow<Double> = _loanValue

    private val _selectedClientId = MutableStateFlow(0)
    val selectedClientId: StateFlow<Int> = _selectedClientId.asStateFlow()

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

    private val _installments = MutableStateFlow("12")
    val installments: StateFlow<String> = _installments

    private val _quotes = MutableStateFlow(1)
    val quotes: StateFlow<Int> = _quotes.asStateFlow()

    private val _quotesDescription = MutableStateFlow("")
    val quotesDescription: StateFlow<String> = _quotesDescription.asStateFlow()

    private val _paymentTypeId = MutableStateFlow(0)
    val paymentTypeId: StateFlow<Int> = _paymentTypeId.asStateFlow()

    private val _paymentTypeDescription = MutableStateFlow("")
    val paymentTypeDescription: StateFlow<String> = _paymentTypeDescription.asStateFlow()

    private val _paymentTypeDays = MutableStateFlow(0)
    val paymentTypeDays: StateFlow<Int> = _paymentTypeDays.asStateFlow()

    private val _generatedPlan = MutableStateFlow<List<Charge>>(emptyList())
    val generatedPlan: StateFlow<List<Charge>> = _generatedPlan

    fun loadClients(){
        val userId = settings.getInt(Constants.USER_ID, 0)
        val dataClients = clientSDK.selectActiveClients(userId.toLong())
        if(dataClients != null) {
            _clients.value = dataClients
        }
    }

    init {
        viewModelScope.launch {
            _loanId.value = settings.getInt(Constants.LOAN_ID, 0)
            interestList = loadInterests()
            loanTypes = loadLoanTypes()
            paymentTypes = loadPaymentTypes()
            chargeTypes = loadChargeTypes()
            loadClients()
        }
    }

    fun onLoanValueChange(value: Double) {
        _loanValue.value = value
    }

    fun onClientSelected(clientId: Int?, clientName: String?) {
        if (clientId != null) {
            _selectedClientId.value = clientId
        }
        if (clientName != null) {
            _selectedClientName.value = clientName
        }
    }

    fun onInterestChange(interest: Interest) {
        _interestId.value = interest.id
        _interestDescription.value = interest.description
        _interestRate.value = interest.value
    }

    fun onTypeChange(type: String) {
        _loanType.value = type
    }

    fun onNumberQuoteSelected(quote: NumberCharge) {
        _quotes.value = quote.number
        _quotesDescription.value = quote.description
    }

    fun onLoanTypeSelected(loanType: LoanType)
    {
        _loanTypeId.value = loanType.id
        _loanType.value = loanType.description
    }

    fun onPaymentTypeSelected(paymentType: PaymentType)
    {
        _paymentTypeId.value = paymentType.id
        _paymentTypeDescription.value = paymentType.description
        _paymentTypeDays.value = paymentType.days
    }

    fun generatePlan() {
        //val principal = loanValue.value.replace(",", "").toDoubleOrNull() ?: return
        val rateDecimal = interestRate.value / 100.0
        val totalInstallments = installments.value.toIntOrNull() ?: return

        //val installmentValue = (principal * (1 + rateDecimal)) / totalInstallments

        val today = CurrentDateDisplay()

        /*val newPlan = (1..totalInstallments).map { number ->
            LoanInstallment(
                number = number,
                total = totalInstallments,
                dueDate = today.plus(months = number),
                value = installmentValue
            )
        }
        _generatedPlan.value = newPlan*/
    }

    fun registerLoan() {
        // Aquí guardarías el préstamo en la BD.
        println("Loan Registered!")
    }

    fun onBackClicked() {
        println("Return loans screen")
        navigation.popBackStack()
    }

    fun showSelectClient(){
        settings.putBoolean(Constants.EXTERNAL_SEARCH_CLIENT, true)
        navigation.navigate("clients")
    }
}