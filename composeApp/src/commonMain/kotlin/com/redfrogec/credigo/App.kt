package com.redfrogec.credigo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
    MaterialTheme (
        typography = typography(),
        colorScheme = colorCompose()
    ){
        settings.putString(Constants.ERROR_CODE,"")
        settings.putString(Constants.ERROR_MESSAGE,"")
        //notificationVm.onEnableNotificationsClicked() //Codigo para llamar la notificacion
        AppNavigation()
    }
}