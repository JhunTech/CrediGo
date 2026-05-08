package com.redfrogec.credigo

import android.app.Application
import androidx.work.Configuration
import com.redfrogec.credigo.backgroundscheduler.PlatformActivityProvider
import com.redfrogec.credigo.di.initializeKoin
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory

class MyApplication : Application(), Configuration.Provider {

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(get())
            .build()

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(PlatformActivityProvider)
        
        initializeKoin(
            config = {
                androidContext(this@MyApplication)
                workManagerFactory()
            }
        )
    }
}
