package com.redfrogec.credigo.di

import com.redfrogec.credigo.data.local.AndroidDatabaseDriverFactory
import com.redfrogec.credigo.data.local.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(androidContext())
    }
}