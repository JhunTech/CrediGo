package com.redfrogec.credigo

import android.os.Build
import com.redfrogec.credigo.backgroundscheduler.PlatformActivityProvider

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun sendToBackground() {
    PlatformActivityProvider.currentActivity?.moveTaskToBack(true)
}