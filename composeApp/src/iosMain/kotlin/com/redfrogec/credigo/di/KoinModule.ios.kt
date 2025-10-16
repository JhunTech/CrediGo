package com.redfrogec.credigo.di

import com.redfrogec.credigo.data.local.DatabaseDriverFactory
import com.redfrogec.credigo.data.local.IOSDatabaseDriverFactory
import org.koin.dsl.module

actual val targetModule = module {
    single<DatabaseDriverFactory> {
        IOSDatabaseDriverFactory()
    }
}