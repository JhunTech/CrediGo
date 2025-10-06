package com.redfrogec.credigo.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Loan

class LoansViewModel(navController: NavController) : ViewModel() {

    private val _navigation = navController

    var selectedTab by mutableStateOf(0) // 0 = Active, 1 = Paid
        private set

    var activeLoans by mutableStateOf(
        listOf(
            Loan("#12345", "$2,500", "12 months", "Due in 3 days"),
            Loan("#67890", "$1,800", "6 months", "Due in 10 days"),
            Loan("#11223", "$3,200", "18 months", "Due in 15 days"),
        )
    )
        private set

    var paidLoans by mutableStateOf(
        listOf(
            Loan("#44556", "$900", "6 months", "Paid"),
            Loan("#77889", "$1,500", "12 months", "Paid")
        )
    )
        private set

    fun onTabSelected(index: Int) {
        selectedTab = index
    }
}