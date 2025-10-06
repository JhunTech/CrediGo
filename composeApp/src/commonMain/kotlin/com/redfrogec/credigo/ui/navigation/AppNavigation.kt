package com.redfrogec.credigo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.redfrogec.credigo.data.model.Screens
import com.redfrogec.credigo.ui.screen.DashboardScreen
import com.redfrogec.credigo.ui.screen.LoginScreen
import com.redfrogec.credigo.ui.screen.PasswordRecoveryScreen
import com.redfrogec.credigo.ui.screen.SignUpScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,
        modifier = modifier
    ) {
        composable(Screens.Login.route) {
            LoginScreen(navController, modifier)
        }

        composable(Screens.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(Screens.PasswordRecovery.route) {
            PasswordRecoveryScreen(navController, modifier)
        }
        composable(Screens.DashBoard.route) {
            DashboardScreen(navController, modifier)
        }
    }
}