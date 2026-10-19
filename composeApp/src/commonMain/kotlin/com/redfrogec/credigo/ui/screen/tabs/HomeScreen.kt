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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.redfrogec.credigo.domain.controls.BarChart
import com.redfrogec.credigo.domain.controls.ClientHomeRow
import com.redfrogec.credigo.domain.controls.MonthData
import com.redfrogec.credigo.domain.utils.currentDateDisplay
import com.redfrogec.credigo.ui.viewModel.ClientsViewModel
import com.redfrogec.credigo.ui.viewModel.HomeViewModel
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_close
import credigo.composeapp.generated.resources.ic_plus
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<HomeViewModel>()
    viewModel.navigation = navController
    val monthlyLoans by viewModel.monthlyLoans.collectAsState()
    val topClients by viewModel.topClients.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(scrollState)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp, 16.dp, 16.dp),
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
                    text = "Home",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(2f).wrapContentWidth(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))

            val currentYear = currentDateDisplay()
            Text(
                text = "Préstamos del ${currentYear.year}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(24.dp))

            BarChart(
                data = monthlyLoans,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Define una altura para el gráfico
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Top de Clientes",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Lista de clientes
            var id = 1
            topClients.forEach { client ->
                ClientHomeRow(client, id)
                id++
            }
        }
    }
}