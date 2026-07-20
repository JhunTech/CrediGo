package com.redfrogec.credigo.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.LoanUI
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.redfrogec.credigo.domain.utils.LoanUtils
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActiveLoansViewModel(
    private val loanSDK: LoanSDK,
    private val chargeSDK: ChargeSDK,
    private val loanUtils: LoanUtils
) : ViewModel() {

    lateinit var navigation: NavController
    private val settings: Settings = Settings()

    private val _defaultActiveLoansUI = MutableStateFlow<List<LoanUI>>(emptyList())
    val defaultActiveLoansUI: StateFlow<List<LoanUI>> = _defaultActiveLoansUI.asStateFlow()

    private val _activeLoansUI = MutableStateFlow<List<LoanUI>>(emptyList())
    val activeLoansUI: StateFlow<List<LoanUI>> = _activeLoansUI.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _showConfirmDelete = MutableStateFlow(false)
    val showConfirmDelete: StateFlow<Boolean> = _showConfirmDelete.asStateFlow()

    private val _selectedIdLoan = MutableStateFlow(-1)
    val selectedIdLoan: StateFlow<Int> = _selectedIdLoan.asStateFlow()

    init {
        viewModelScope.launch {
            loadLoans()
        }
    }

    fun loadLoans() {
        val loans = loanUtils.loadLoansInfo(false)
        if(loans.isNotEmpty()){
            _activeLoansUI.value = loans
            _defaultActiveLoansUI.value = loans
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _activeLoansUI.update { list ->
            if (query.isBlank()) _defaultActiveLoansUI.value
            else _defaultActiveLoansUI.value.filter { it.clientName.contains(query, ignoreCase = true) }
        }
    }

    fun onAddClick() {
        settings.putLong(Constants.LOAN_ID, -1)
        navigation.navigate("newLoan")
    }

    fun deleteLoan(id: Int) {
        val deleteQuotes = chargeSDK.deleteAllCharges(id.toLong())
        if(deleteQuotes > 0) {
            print("Cuotas borradas correctamente con ID: $id")
            val deleteLoan = loanSDK.deleteLoan(id.toLong())
            if(deleteLoan.toInt() > 0) {
                print("Préstamo borrado correctamente con ID: $id")
            }
            else{
                print("Error al borrar el Préstamo con el ID: $id")
            }
        }
        else{
            print("Error al borrar las cuotas con el ID: $id")
        }
        updateLoanList()
    }

    fun updateLoanList(){
        navigation.popBackStack()
        navigation.navigate("activeLoans")
    }

    fun onConfirmDeleteChanged(newValue: Boolean, id: Int) {
        _showConfirmDelete.value = newValue
        _selectedIdLoan.value = id
    }

    fun onShowQuotes(id: Long) {
        settings.putLong(Constants.LOAN_ID, id)
        navigation.navigate("listQuotes")
    }

    fun onPayLoanClick() {
        navigation.navigate("paidLoans")
    }
}