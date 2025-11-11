package com.redfrogec.credigo.ui.screen.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.redfrogec.credigo.ui.viewModel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel = viewModel { HomeViewModel(navController) }
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Home",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))

            val monthlySales = listOf(
                MonthData("Ene", 1500.35f),
                MonthData("Feb", 2200f),
                MonthData("Mar", 1800f),
                MonthData("Abr", 2700.86f),
                MonthData("May", 3100f),
                MonthData("Jun", 1900f),
                MonthData("Jul", 1500.35f),
                MonthData("Ago", 2200f),
                MonthData("Sep", 1200f),
                MonthData("Oct", 2700.86f),
                MonthData("Nov", 10000f),
                MonthData("Dic", 1950f)
            )

            BarChart(
                data = monthlySales,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Define una altura para el gráfico
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Top Clients with Outstanding Debts",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Lista de clientes
            viewModel.clients.forEach { client ->
                ClientHomeRow(client)
            }
        }
    }
}