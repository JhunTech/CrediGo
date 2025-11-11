package com.redfrogec.credigo.data.model

import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_clients
import credigo.composeapp.generated.resources.ic_home
import credigo.composeapp.generated.resources.ic_loans
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.DrawableResource

sealed class Screens(val icon: DrawableResource, val label: String, val route: String) {
    //Initial Screen
    data object Login : Screens(icon = Res.drawable.ic_home, "", "login")
    data object SignUp : Screens(icon = Res.drawable.ic_home, "", "signup")
    data object PasswordRecovery : Screens(icon = Res.drawable.ic_home, "", "passwordrecovery")
    data object DashBoard : Screens(icon = Res.drawable.ic_home, "", "dashboard")
    //Tabs
    data object Home : Screens(icon = Res.drawable.ic_home, "Home", "home")
    data object Loans : Screens(icon = Res.drawable.ic_loans, "Préstamo", "loans")
    data object Clients : Screens(icon = Res.drawable.ic_clients, "Clientes", "clients")
    data object User : Screens(icon = Res.drawable.ic_user, "Usuario", "user")
    //New Tab
    data object NewClient : Screens(icon = Res.drawable.ic_home, "NewClient", "newclient")
}

val listOfTabScreens = listOf(
    Screens.Home, Screens.Loans, Screens.Clients, Screens.User
)