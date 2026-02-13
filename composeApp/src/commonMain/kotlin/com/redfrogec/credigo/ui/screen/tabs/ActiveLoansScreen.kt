package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.loanUI
import com.redfrogec.credigo.domain.controls.LoanItem
import com.redfrogec.credigo.domain.controls.confirmDialog
import com.redfrogec.credigo.ui.viewModel.ActiveLoansViewModel
import com.redfrogec.credigo.ui.viewModel.SharedViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import credigo.composeapp.generated.resources.ic_loans
import credigo.composeapp.generated.resources.ic_plus
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveLoansScreen(navController: NavController, modifier: Modifier) {

    val viewModel = koinViewModel<ActiveLoansViewModel>()
    viewModel.navigation = navController
    val sharedViewModel = koinViewModel<SharedViewModel>()
    val activeLoansUI by viewModel.activeLoansUI.collectAsState()
    val showConfirmDelete by viewModel.showConfirmDelete.collectAsState()
    val selectedIdLoan by viewModel.selectedIdLoan.collectAsState()

    if(showConfirmDelete)
    {
        val dialogResponse = confirmDialog("Confirmación", "Estás seguro(a) de eliminar este préstamo?")
        if(dialogResponse == "OK")
        {
            viewModel.deleteLoan(selectedIdLoan)
        }
        else if(dialogResponse == "Cancel")
        {
            viewModel.updateLoanList()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier
                        .size(25.dp)
                        .padding(start = 16.dp)
                        .clickable{ viewModel.onPayLoanClick() }
                        .weight(1f).wrapContentWidth(Alignment.Start),
                    painter = painterResource(Res.drawable.ic_loans),
                    contentDescription = "PaidLoans",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Prést. Activos",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2f).wrapContentWidth(Alignment.CenterHorizontally)
                )

                IconButton(
                    onClick = { viewModel.onAddClick()},
                    modifier = Modifier.weight(1f)
                        .wrapContentWidth(Alignment.End)
                        .padding(end = 3.dp)
                ) {
                    Icon(
                        modifier = modifier
                            .size(25.dp)
                            .padding(0.dp),
                        painter = painterResource(Res.drawable.ic_plus),
                        contentDescription = "AddLoan",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if(activeLoansUI != null){
                    LoanList(activeLoansUI!!, viewModel)
                }
            }
        }
    }
}

@Composable
fun LoanList(loans: List<loanUI>, viewModel: ActiveLoansViewModel){
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 2.dp)
    ) {
        items(loans, key = { it.id }) { loan ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    when (value) {
                        SwipeToDismissBoxValue.StartToEnd -> { // Izquierda → Eliminar
                            viewModel.onConfirmDeleteChanged(true, loan.id.toInt())
                            true
                        }

                        SwipeToDismissBoxValue.EndToStart -> { // Derecha → Cuotas
                            viewModel.onShowQuotes(loan.id)
                            true
                        }

                        else -> false
                    }
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val color = when (dismissState.dismissDirection) {
                        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.secondaryContainer // Verde
                        SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.error // Rojo
                        else -> Color.Transparent
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 16.dp),
                        contentAlignment = when (dismissState.dismissDirection) {
                            SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                            SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                            else -> Alignment.Center
                        }
                    ) {
                        Text(
                            text = when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.EndToStart -> "Cuotas"
                                SwipeToDismissBoxValue.StartToEnd -> "Eliminar"
                                else -> ""
                            },
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                },
                content = {
                    LoanItem(loan)
                }
            )
        }
    }
}