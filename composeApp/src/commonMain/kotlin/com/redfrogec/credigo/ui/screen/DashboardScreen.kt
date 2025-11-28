package com.redfrogec.credigo.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.redfrogec.credigo.data.model.Screens
import com.redfrogec.credigo.data.model.listOfTabScreens
import com.redfrogec.credigo.ui.screen.tabs.ClientsScreen
import com.redfrogec.credigo.ui.screen.tabs.HomeScreen
import com.redfrogec.credigo.ui.screen.tabs.LoansScreen
import com.redfrogec.credigo.ui.screen.tabs.NewClientScreen
import com.redfrogec.credigo.ui.screen.tabs.NewLoanScreen
import com.redfrogec.credigo.ui.screen.tabs.ProfileScreen
import com.redfrogec.credigo.ui.viewModel.DashboardViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardScreen(navControllerInitial: NavController, modifier: Modifier) {

    val navController = rememberNavController()
    val dashboardViewModel = viewModel<DashboardViewModel>()


    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
    }) { innerPadding ->

        val graph =
            navController.createGraph(startDestination = Screens.Home.route) {
                composable(route = Screens.Home.route) {
                    HomeScreen(navController)
                }
                composable(route = Screens.Loans.route) {
                    LoansScreen(navController, modifier)
                }
                composable(route = Screens.Clients.route) {
                    ClientsScreen(navController, modifier)
                }
                composable(route = Screens.User.route) {
                    ProfileScreen(navController)
                }

                //New Pages on tab
                composable(route = Screens.NewClient.route) {
                    NewClientScreen(navController, modifier)
                }
                composable(route = Screens.NewLoan.route) {
                    NewLoanScreen(navController, modifier)
                }
            }
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
fun BottomNavigationBar (navController: NavHostController){
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        listOfTabScreens.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon), contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if(index == selectedNavigationIndex.intValue)
                            Color.Black
                        else MaterialTheme.colorScheme.secondary
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        overflow = TextOverflow.Ellipsis,
                        color = if (index == selectedNavigationIndex.intValue)
                            Color.Black
                        else MaterialTheme.colorScheme.secondary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )

            )
        }
    }
}
