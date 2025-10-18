package com.redfrogec.credigo.domain.sdk

import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.utils.cleanErrorData
import com.russhwolf.settings.Settings
import kotlinx.datetime.LocalDateTime

class ClientSDK(
    private val database: LocalDatabase
) {
    private val settings: Settings = Settings()
    @Throws(Exception::class)

    suspend fun selectAllClients(userId: Long): List<Client>? {
        cleanErrorData()
        return try{
            database.selectAllClients(userId)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C001")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    suspend fun insertClient(image: String?, userId: Long, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime){
        cleanErrorData()
        try {
            database.insertClient(image, userId, identification, name, email, phone, address, blocked, registerDate)
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }

    suspend fun updateDataClient(id: Long, image: String?, userId: Long, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime){
        cleanErrorData()
        try {
            database.updateDataClient(
                id,
                image,
                userId,
                identification,
                name,
                email,
                phone,
                address,
                blocked,
                registerDate
            )
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C003")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }

    suspend fun deleteClient(id: Long) {
        cleanErrorData()
        try {
            database.deleteClient(id)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C004")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }
}