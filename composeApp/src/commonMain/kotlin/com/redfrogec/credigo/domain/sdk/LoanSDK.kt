package com.redfrogec.credigo.domain.sdk

import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.Loan
import com.redfrogec.credigo.domain.utils.cleanErrorData
import com.russhwolf.settings.Settings

class LoanSDK(
    private val database: LocalDatabase
) {
    private val settings: Settings = Settings()
    @Throws(Exception::class)

    fun selectAllLoansByClientId(clientId: Long, active: Boolean): List<Loan>?{
        cleanErrorData()
        return try{
            database.selectAllLoansByClientId(clientId, active)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L001")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            emptyList()
        }
    }

    fun selectAllLoansByUserId(userId: Long, active: Boolean): List<Loan> {
        cleanErrorData()
        return try{
            database.selectAllLoansByUserId(userId, active)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            emptyList()
        }
    }

    fun selectLoanById(id: Long): Loan? {
        cleanErrorData()
        return try{
            database.selectLoanById(id)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L003")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    fun insertLoan (clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String, loanTypeId: Long): Long  {
        cleanErrorData()
        return try {
            database.insertLoan(clientId, value, paymentTypeId, interestId, quotaNumbers, active, creationDate, deliveryDate, loanTypeId).value
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L004")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun updateLoan(clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String,loanTypeId: Long,  id: Long): Long  {
        cleanErrorData()
        return try {
            database.updateLoan(clientId, value, paymentTypeId, interestId, quotaNumbers, active, creationDate, deliveryDate ,loanTypeId , id).value
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L005")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun deleteLoan(id: Long): Long {
        cleanErrorData()
        return try {
            database.deleteLoan(id).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L006")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun updateLoanState(active: Boolean, id: Long): Long  {
        cleanErrorData()
        return try {
            database.updateLoanState(active, id).value
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L007")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }
}