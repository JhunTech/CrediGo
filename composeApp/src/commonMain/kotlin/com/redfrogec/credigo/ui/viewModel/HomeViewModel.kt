package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.ClientStatus
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.Loan
import com.redfrogec.credigo.data.model.LoanDate
import com.redfrogec.credigo.data.model.TopClient
import com.redfrogec.credigo.domain.controls.MonthData
import com.redfrogec.credigo.domain.sdk.ChargeSDK
import com.redfrogec.credigo.domain.sdk.LoanSDK
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.parse
import kotlinx.datetime.number
import kotlinx.datetime.parse

class HomeViewModel(private val loanSDK: LoanSDK, private val chargeSDK: ChargeSDK) : ViewModel() {

    lateinit var navigation: NavController
    private val settings: Settings = Settings()

    private val _monthlyLoans = MutableStateFlow<List<MonthData>>(emptyList())
    val monthlyLoans: StateFlow<List<MonthData>> = _monthlyLoans.asStateFlow()

    private val _topClients = MutableStateFlow<List<TopClient>>(emptyList())
    val topClients: StateFlow<List<TopClient>> = _topClients.asStateFlow()

    init {
        viewModelScope.launch {
            loadHome()
        }
    }

    fun loadMonthlyLoans(): List<MonthData>{
        val monthlySales = listOf(
            MonthData("Ene", 0f),
            MonthData("Feb", 0f),
            MonthData("Mar", 0f),
            MonthData("Abr", 0f),
            MonthData("May", 0f),
            MonthData("Jun", 0f),
            MonthData("Jul", 0f),
            MonthData("Ago", 0f),
            MonthData("Sep", 0f),
            MonthData("Oct", 0f),
            MonthData("Nov", 0f),
            MonthData("Dic", 0f)
        )
        return monthlySales
    }

    fun loadHome(){
        val userId = settings.getLong(Constants.USER_ID, 0)
        val activeLoans = loanSDK.selectAllLoansByUserId(userId, true)
        val currentYear = currentDateDisplay()
        loadLoans(activeLoans, currentYear)
        loadClients(activeLoans, currentYear)
    }

    fun loadLoans(activeLoans: List<Loan>, currentYear: LocalDateTime){
        val loadMonthlyLoans = loadMonthlyLoans()
        if (activeLoans.isNotEmpty()) {
            activeLoans.forEach { loan ->
                val creationDate = LocalDateTime.parse(loan.creationDate).date
                if(loan.active && creationDate.year == currentYear.year){
                    when (creationDate.month.number) {
                        1 -> loadMonthlyLoans[0].value += loan.value.toFloat()
                        2 -> loadMonthlyLoans[1].value += loan.value.toFloat()
                        3 -> loadMonthlyLoans[2].value += loan.value.toFloat()
                        4 -> loadMonthlyLoans[3].value += loan.value.toFloat()
                        5 -> loadMonthlyLoans[4].value += loan.value.toFloat()
                        6 -> loadMonthlyLoans[5].value += loan.value.toFloat()
                        7 -> loadMonthlyLoans[6].value += loan.value.toFloat()
                        8 -> loadMonthlyLoans[7].value += loan.value.toFloat()
                        9 -> loadMonthlyLoans[8].value += loan.value.toFloat()
                        10 -> loadMonthlyLoans[9].value += loan.value.toFloat()
                        11 -> loadMonthlyLoans[10].value += loan.value.toFloat()
                        12 -> loadMonthlyLoans[11].value += loan.value.toFloat()
                    }
                }
            }
            _monthlyLoans.value = loadMonthlyLoans
        }
    }

    fun loadClients(activeLoans: List<Loan>, currentYear: LocalDateTime){
        if (activeLoans.isNotEmpty()) {
            println("Tamaño de la lista activeLoans: ${activeLoans.size}")
            val topClientNoSum= mutableListOf<TopClient>()
            activeLoans.forEach { loan ->
                val creationDate = LocalDateTime.parse(loan.creationDate).date
                if(creationDate.year == currentYear.year){
                    println("Pasamos la fecha de creación: ${currentYear.year}")
                    val totalCharges = chargeSDK.selectChargesTotals(loan.id)
                    if(totalCharges != null){
                        println("Total de cobros del id de loan: ${loan.id}, el valor del préstamo total es ${totalCharges.totalAmountQuota} y el total pagado es ${totalCharges.totalAmountCharged}, además del total de remanente: ${totalCharges.totalAmountRemaining}")
                        val topClient = TopClient(
                            loan.clientName,
                            totalCharges.totalAmountQuota,
                            totalCharges.totalAmountCharged)
                        topClientNoSum.add(topClient)
                    }
                }
            }
            println("Tamaño de la lista topClientNoSum: ${topClientNoSum.size}")
            if(topClientNoSum.isNotEmpty()){
                val topClientFilter =  topClientNoSum
                    .groupBy { it.clientName }
                    .map { (name, items) ->
                        TopClient(
                            clientName = name,
                            totalLoans = items.sumOf { it.totalLoans },
                            totalCharges = items.sumOf { it.totalCharges }
                        )
                    }
                    .sortedByDescending { it.totalLoans }
                    .take(5)
                println("Tamaño de la lista topClientFilter: ${topClientFilter.size}")
                if(topClientFilter.isNotEmpty()){
                    _topClients.value = topClientFilter
                }
            }
        }
    }
}