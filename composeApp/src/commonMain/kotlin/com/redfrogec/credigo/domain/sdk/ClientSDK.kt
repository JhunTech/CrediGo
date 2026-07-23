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

    fun selectAllClients(userId: Long): List<Client>? {
        cleanErrorData()
        return try{
            database.selectAllClients(userId)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C001")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    fun selectActiveClients(userId: Long): List<Client>? {
        cleanErrorData()
        return try{
            database.selectActiveClients(userId)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    fun selectClientById(id: Long): Client? {
        cleanErrorData()
        return try{
            database.selectClientById(id)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C003")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    fun insertClient(userId: Long, image: String?, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime): Long {
        cleanErrorData()
        return try {
            database.insertClient(userId, image, identification, name, email, phone, address, blocked, registerDate).value
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C004")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun updateDataClient( userId: Long, image: String?, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime, id: Long): Long {
        cleanErrorData()
        return try {
            database.updateDataClient(
                userId,
                image,
                identification,
                name,
                email,
                phone,
                address,
                blocked,
                registerDate,
                id
            ).value
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C005")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun deleteClient(id: Long): Long {
        cleanErrorData()
        return try {
            database.deleteClient(id).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "C006")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }
}