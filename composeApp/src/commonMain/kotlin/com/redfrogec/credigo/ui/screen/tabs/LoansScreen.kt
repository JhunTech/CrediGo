package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.redfrogec.credigo.data.model.loanUI
import com.redfrogec.credigo.ui.viewModel.LoansViewModel
import credigo.composeapp.generated.resources.Res
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
    val paidLoansUI by viewModel.paidLoansUI.collectAsState()
    val activeLoansUI by viewModel.activeLoansUI.collectAsState()
    val loans = if (selectedTab == 0) paidLoansUI else activeLoansUI

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
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Préstamos",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2f).wrapContentWidth(Alignment.CenterHorizontally)
                )
                IconButton(
                    onClick = { viewModel.onAddClick()},
                    modifier = Modifier.weight(1f).wrapContentWidth(Alignment.End)
                ) {
                    Icon(
                        modifier = modifier
                            .size(30.dp)
                            .padding(0.dp),
                        painter = painterResource(Res.drawable.ic_plus),
                        contentDescription = "Agregar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            TabRow(selectedTabIndex = selectedTab, containerColor = Color.Transparent) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { viewModel.onTabSelected(0) },
                    text = { Text(
                        text = "Activos",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall
                    ) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { viewModel.onTabSelected(1) },
                    text = { Text(
                        text = "Pagados",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall
                    ) }
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if(loans != null && loans.isNotEmpty()){
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(0.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(loans, key = { it.id }) { loan ->
                            LoanItem(loan)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoanItem(loan: loanUI) {
    /*Row(
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
    }*/

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFFE6F4EF), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(Res.drawable.ic_credit_card),
                contentDescription = null
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(loan.clientName, fontWeight = FontWeight.Bold)
            Text(loan.loanNumber, color = Color(0xFF00A86B))
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                "$${loan.value}",
                fontWeight = FontWeight.Bold
            )
            Text(
                "${loan.quotaInfo} • ${loan.dueInfo}",
                color = Color(0xFF00A86B),
                fontSize = 12.sp
            )
        }
    }
}