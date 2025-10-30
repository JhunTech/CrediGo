package com.redfrogec.credigo.domain.sdk

import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.data.model.Loan
import com.redfrogec.credigo.domain.utils.cleanErrorData
import com.russhwolf.settings.Settings

class LoanSDK(
    private val database: LocalDatabase
) {
    private val settings: Settings = Settings()
    @Throws(Exception::class)

    suspend fun selectAllLoansByClientId(clientId: Long, active: Boolean): List<Loan>?{
        cleanErrorData()
        return try{
            database.selectAllLoansByClientId(clientId, active)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L001")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    suspend fun selectAllLoansByUserId(userId: Long, active: Boolean): List<Loan> {
        cleanErrorData()
        return try{
            database.selectAllLoansByUserId(userId, active)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            emptyList()
        }
    }

    suspend fun insertLoan (clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String, loanTypeId: Long) {
        cleanErrorData()
        try {
            database.insertLoan(clientId, value, paymentTypeId, interestId, quotaNumbers, active, creationDate, deliveryDate, loanTypeId)
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L003")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }

    suspend fun updateDataLoan(clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String,loanTypeId: Long,  id: Long) {
        cleanErrorData()
        try {
            database.updateDataLoan(clientId, value, paymentTypeId, interestId, quotaNumbers, active, creationDate, deliveryDate ,loanTypeId , id)
        }catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L004")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }

    suspend fun deleteLoan(id: Long) {
        cleanErrorData()
        try {
            database.deleteLoan(id)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "L005")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
        }
    }
}