package com.redfrogec.credigo.domain.sdk

import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.User
import com.redfrogec.credigo.domain.utils.cleanErrorData
import com.russhwolf.settings.Settings
import kotlinx.datetime.LocalDateTime

class UserSDK(
    private val database: LocalDatabase
){
    private val settings: Settings = Settings()
    @Throws(Exception::class)
    fun findUserByEmailAndPassword(email: String, password: String): User? {
        cleanErrorData()
        return try{
            database.findUserByEmailAndPassword(email, password)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U001")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    fun insertUser(image: String?, email: String, name: String, passwordHash: String, questionId: Long, response: String, registerDate: LocalDateTime, updateDate: LocalDateTime?, tokenId: Long?, token: String?, tokenExpire: LocalDateTime?): Boolean {
        cleanErrorData()
        try{
            database.insertUser(image, email, name, passwordHash, questionId, response, registerDate, updateDate, tokenId, token, tokenExpire)
            return true
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            return false
        }
    }

    fun updateUser(image: String?, email: String, name: String, passwordHash: String, questionId: Long, response: String, registerDate: LocalDateTime, updateDate: LocalDateTime?, tokenId: Long?, token: String?, tokenExpire: LocalDateTime?, id: Long) {
        cleanErrorData()
        try {
            database.updateUser(
                image,
                email,
                name,
                passwordHash,
                questionId,
                response,
                registerDate,
                updateDate,
                tokenId,
                token,
                tokenExpire,
                id
            )
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U003")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }

    fun deleteUser(id: Long) {
        cleanErrorData()
        try {
            database.deleteUser(id)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U004")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }
}