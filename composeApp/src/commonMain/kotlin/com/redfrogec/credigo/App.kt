package com.redfrogec.credigo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.ui.navigation.AppNavigation
import com.redfrogec.credigo.ui.theme.colorCompose
import com.redfrogec.credigo.ui.theme.typography
import com.russhwolf.settings.Settings
import org.jetbrains.compose.ui.tooling.preview.Preview

private val settings: Settings = Settings()

@Composable
@Preview
fun App() {
    MaterialTheme (
        typography = typography(),
        colorScheme = colorCompose()
    ){
        settings.putString(Constants.ERROR_CODE,"")
        settings.putString(Constants.ERROR_MESSAGE,"")

        AppNavigation()
    }
}