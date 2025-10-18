package com.redfrogec.credigo.domain.utils

import com.redfrogec.credigo.data.model.Constants
import com.russhwolf.settings.Settings

private val settings: Settings = Settings()

fun cleanErrorData(){
    settings.putString(Constants.ERROR_CODE, "")
    settings.putString(Constants.ERROR_MESSAGE, "")
}

fun clearAppSettings(){
    settings.clear()
}