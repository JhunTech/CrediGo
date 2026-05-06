package com.redfrogec.credigo.di

import com.redfrogec.credigo.backgroundscheduler.LocalNotificationManager
import com.redfrogec.credigo.backgroundscheduler.initializeBackgroundScheduler
import com.redfrogec.credigo.data.local.AndroidDatabaseDriverFactory
import com.redfrogec.credigo.data.local.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(androidContext())
    }

    single { LocalNotificationManager(androidContext()) }
    single { initializeBackgroundScheduler(androidContext()) }
}