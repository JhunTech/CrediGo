package com.redfrogec.credigo.data.model

import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.ic_clients
import credigo.composeapp.generated.resources.ic_dollar
import credigo.composeapp.generated.resources.ic_home
import credigo.composeapp.generated.resources.ic_loans
import credigo.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.DrawableResource

sealed class Screens(val icon: DrawableResource, val label: String, val route: String) {
    //Initial Screen
    data object Login : Screens(icon = Res.drawable.ic_home, "", "login")
    data object SignUp : Screens(icon = Res.drawable.ic_home, "", "signup")
    data object PasswordRecovery : Screens(icon = Res.drawable.ic_home, "", "passwordRecovery")
    data object DashBoard : Screens(icon = Res.drawable.ic_home, "", "dashboard")
    //Tabs
    data object Home : Screens(icon = Res.drawable.ic_home, "Home", "home")
    data object ActiveLoans : Screens(icon = Res.drawable.ic_dollar, "Préstamo", "activeloans")
    data object Clients : Screens(icon = Res.drawable.ic_clients, "Clientes", "clients")
    data object User : Screens(icon = Res.drawable.ic_user, "Usuario", "user")
    //New Tab
    data object NewClient : Screens(icon = Res.drawable.ic_home, "NewClient", "newClient")
    data object NewLoan : Screens(icon = Res.drawable.ic_home, "NewLoan", "newLoan")
    data object ListQuotes : Screens(icon = Res.drawable.ic_home, "ListQuotes", "listQuotes")
    data object PaidLoans : Screens(icon = Res.drawable.ic_loans, "P. Cob.", "paidloans")
}

val listOfTabScreens = listOf(
    Screens.Home, Screens.ActiveLoans, Screens.Clients, Screens.User
)