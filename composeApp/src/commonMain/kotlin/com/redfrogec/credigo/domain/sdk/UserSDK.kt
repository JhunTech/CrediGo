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

    fun insertUser(image: String?, email: String, name: String, passwordHash: String, questionId: Long, response: String, registerDate: LocalDateTime, updateDate: LocalDateTime?, tokenId: Long?, token: String?, tokenExpire: LocalDateTime?): Long {
        cleanErrorData()
        return try{
            database.insertUser(image, email, name, passwordHash, questionId, response, registerDate, updateDate, tokenId, token, tokenExpire).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun updateUser(image: String?, email: String, name: String, passwordHash: String, questionId: Long, response: String, registerDate: LocalDateTime, updateDate: LocalDateTime?, tokenId: Long?, token: String?, tokenExpire: LocalDateTime?, id: Long): Long {
        cleanErrorData()
        return try {
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
            ).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U003")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun recoveryPasswordUserByEmailAndQuestion(passwordHash: String, email: String, questionId: Long, response: String): Long {
        cleanErrorData()
        return try {
            database.recoveryPasswordUserByEmailAndQuestion(
                passwordHash,
                email,
                questionId,
                response
            ).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U004")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun deleteUser(id: Long): Long {
        cleanErrorData()
        return try {
            database.deleteUser(id).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U005")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun deleteUserData(id: Long): Long {
        cleanErrorData()
        return try {
            database.deleteUserData(id).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U006")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun updatePassword(passwordHash: String, id: Long): Long {
        cleanErrorData()
        return try {
            database.updatePassword(
                passwordHash,
                id
            ).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U007")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun selectDataUser(id: Long): User? {
        cleanErrorData()
        return try{
            database.selectDataUser(id)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "U008")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }
}