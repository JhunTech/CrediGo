package com.redfrogec.credigo.domain.sdk

import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.utils.cleanErrorData
import com.russhwolf.settings.Settings

class KeySDK(
    private val database: LocalDatabase
) {
    private val settings: Settings = Settings()
    @Throws(Exception::class)

    suspend fun selectAllKey(): String?{
        cleanErrorData()
        return try{
            database.selectAllKey()
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "K001")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    suspend fun insertKey(key: String) {
        cleanErrorData()
        try {
            database.insertKey(key)
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "K002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }

    suspend fun deleteKey(){
        cleanErrorData()
        try {
            database.deleteKey()
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "K003")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }
}