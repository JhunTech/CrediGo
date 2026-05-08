package com.redfrogec.credigo.di

import androidx.work.WorkManager
import com.redfrogec.credigo.backgroundscheduler.BackgroundWorker
import com.redfrogec.credigo.backgroundscheduler.LocalNotificationManager
import com.redfrogec.credigo.backgroundscheduler.initializeBackgroundScheduler
import com.redfrogec.credigo.data.local.AndroidDatabaseDriverFactory
import com.redfrogec.credigo.data.local.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(androidContext())
    }

    single { LocalNotificationManager(androidContext()) }

    // Registro del Worker para inyección de dependencias con Koin
    workerOf(::BackgroundWorker)

    // Proveedor de WorkManager
    single { WorkManager.getInstance(androidContext()) }

    // Declaración de la llamada de inicialización del scheduler
    // Usamos createdAtStart para que se ejecute automáticamente al iniciar Koin
    single(createdAtStart = true) { initializeBackgroundScheduler(get()) }
}
