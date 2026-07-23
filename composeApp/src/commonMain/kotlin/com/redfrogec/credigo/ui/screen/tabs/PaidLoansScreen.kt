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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.redfrogec.credigo.domain.controls.LoanItem
import com.redfrogec.credigo.ui.viewModel.PaidLoansViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_arrow_left
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaidLoansScreen(navController: NavController, modifier: Modifier) {

    val viewModel = koinViewModel<PaidLoansViewModel>()
    viewModel.navigation = navController
    val paidLoansUI by viewModel.paidLoansUI.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                        .clickable { viewModel.onBackClicked() }
                        .weight(1f).wrapContentWidth(Alignment.Start),
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Prést. Cobrados",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2f).wrapContentWidth(Alignment.CenterHorizontally)
                )

                // Espaciador flexible después del título (mantiene el centro visual)
                Spacer(modifier = Modifier.weight(1f))
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if(paidLoansUI != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(0.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(paidLoansUI!!, key = { it.id }) { loan ->
                            LoanItem(loan)
                        }
                    }
                }
            }
        }
    }
}
