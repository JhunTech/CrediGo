package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.Loan
import com.redfrogec.credigo.ui.viewModel.LoansViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import credigo.composeapp.generated.resources.ic_credit_card
import credigo.composeapp.generated.resources.ic_plus
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansScreen(navController: NavController, modifier: Modifier) {

    val viewModel = koinViewModel<LoansViewModel>()
    viewModel.navigation = navController
    val scrollState = rememberScrollState()
    val selectedTab = viewModel.selectedTab
    val loans = if (selectedTab == 0) viewModel.activeLoans else viewModel.paidLoans

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        /*Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Préstamos",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { viewModel.onAddClick() },
                            modifier = modifier
                                .size(30.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_plus),
                                contentDescription = "Agregar",
                                modifier = modifier
                                    .fillMaxSize()
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(0.dp)
                )
            }
        ) { padding ->*/
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TabRow(selectedTabIndex = selectedTab, containerColor = Color.Transparent) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { viewModel.onTabSelected(0) },
                        text = { Text("Activos") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { viewModel.onTabSelected(1) },
                        text = { Text("Pagados") }
                    )
                }
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    loans.forEach { loan ->
                        LoanItem(loan)
                    }
                }
            }
        }
    }
//}

@Composable
fun LoanItem(loan: Loan) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_credit_card), contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(loan.id, fontSize = 16.sp, color = Color.Black)
            Text("${loan.value} · ${loan.creationDate}", fontSize = 14.sp, color = Color(0xFF3BA776))
        }

        Text("Cuotas: ${loan.quotaNumbers}", fontSize = 14.sp, color = Color.Black)
    }
}