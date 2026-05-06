package com.redfrogec.credigo

import android.app.Application
import com.redfrogec.credigo.backgroundscheduler.PlatformActivityProvider

import com.redfrogec.credigo.backgroundscheduler.initializeBackgroundScheduler
import com.redfrogec.credigo.backgroundscheduler.setAppContext
import com.redfrogec.credigo.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(PlatformActivityProvider)
        initializeKoin(
            config = { androidContext(this@MyApplication) }
        )
    }
}
