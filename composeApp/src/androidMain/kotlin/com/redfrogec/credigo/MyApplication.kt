package com.redfrogec.credigo

import android.app.Application
import androidx.work.Configuration
import com.redfrogec.credigo.backgroundscheduler.PlatformActivityProvider
import com.redfrogec.credigo.di.initializeKoin
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext

class MyApplication : Application(), Configuration.Provider {

    override val workManagerConfiguration: Configuration
        get() {
            // WorkManager might initialize before onCreate in some circumstances.
            // We ensure Koin is available before providing the worker factory.
            ensureKoinInitialized()
            return Configuration.Builder()
                .setWorkerFactory(get())
                .build()
        }

    override fun onCreate() {
        super.onCreate()

        ensureKoinInitialized()
        
        // Track activities for context-aware operations like permission requests
        registerActivityLifecycleCallbacks(PlatformActivityProvider)
    }

    private fun ensureKoinInitialized() {
        if (GlobalContext.getOrNull() == null) {
            initializeKoin {
                androidContext(this@MyApplication)
                workManagerFactory()
            }
        }
    }
}