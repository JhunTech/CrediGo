package com.redfrogec.credigo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.ui.navigation.AppNavigation
import com.redfrogec.credigo.ui.theme.colorCompose
import com.redfrogec.credigo.ui.theme.typography
import com.redfrogec.credigo.ui.viewModel.NotificationViewModel
import com.russhwolf.settings.Settings
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

private val settings: Settings = Settings()

@Composable
@Preview
fun App(
    //notificationVm : NotificationViewModel = koinInject() //Inyeccion para notificacion
) {
    LaunchedEffect(Unit) {
        settings.putString(Constants.ERROR_CODE,"")
        settings.putString(Constants.ERROR_MESSAGE,"")
    }

    MaterialTheme (
        typography = typography(),
        colorScheme = colorCompose()
    ){
        //notificationVm.onEnableNotificationsClicked() //Codigo para llamar la notificacion
        AppNavigation()
    }
}