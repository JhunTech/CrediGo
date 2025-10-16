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
            Loan("#12345", "$2,500", "12 months", 100.00, 0, 0, 10, true, "2023-08-01", "2023-09-01"),
            Loan("#67890", "$1,800", "6 months", 100.00, 0, 0, 10, true, "2023-08-01", "2023-09-01"),
            Loan("#11223", "$3,200", "18 months", 100.00, 0, 0, 10, true, "2023-08-01", "2023-09-01")
        )
    )
        private set

    var paidLoans by mutableStateOf(
        listOf(
            Loan("#44556", "$900", "6 months", 100.00, 0, 0, 10, true, "2023-08-01", "2023-09-01"),
            Loan("#77889", "$1,500", "12 months", 100.00, 0, 0, 10, true, "2023-08-01", "2023-09-01")
        )
    )
        private set

    fun onTabSelected(index: Int) {
        selectedTab = index
    }
}