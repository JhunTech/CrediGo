package com.redfrogec.credigo.backgroundscheduler

import kotlinx.coroutines.yield

// Función común que se ejecutará en segundo plano en ambas plataformas
suspend fun performSharedBackgroundWork() {
    println("CrediGo: Ejecutando tarea compartida en segundo plano...")
    // yield() permite que la corrutina verifique si ha sido cancelada
    yield()
    // Aquí puedes agregar la lógica que desees ejecutar periódicamente
}

expect fun initializeBackgroundScheduler()
