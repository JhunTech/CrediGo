package com.redfrogec.credigo.backgroundscheduler

import androidx.compose.runtime.Composable
import com.redfrogec.credigo.ui.viewModel.NotificationViewModel
import kotlinx.coroutines.yield
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

// Función común que se ejecutará en segundo plano en ambas plataformas
suspend fun performSharedBackgroundWork(): String {
    println("CrediGo: Ejecutando tarea compartida en segundo plano...")
    yield()
    return "CrediGo: Tarea compartida completada"
}
