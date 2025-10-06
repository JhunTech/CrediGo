package com.redfrogec.credigo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.redfrogec.credigo.ui.navigation.AppNavigation
import com.redfrogec.credigo.ui.screen.SignUpScreen
import com.redfrogec.credigo.ui.theme.colorCompose
import com.redfrogec.credigo.ui.theme.typography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme (
        typography = typography(),
        colorScheme = colorCompose()
    ){
        AppNavigation()
    }
}