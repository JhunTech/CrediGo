package com.redfrogec.credigo.domain.sdk

import com.redfrogec.credigo.data.local.LocalDatabase
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.data.model.ChargePaid
import com.redfrogec.credigo.data.model.Constants
import com.redfrogec.credigo.domain.utils.cleanErrorData
import com.russhwolf.settings.Settings

class ChargeSDK(
    private val database: LocalDatabase
) {

    private val settings: Settings = Settings()
    @Throws(Exception::class)

    suspend fun selectAllChargeByLoanId(loanId: Long): List<Charge>{
        cleanErrorData()
        return try{
            database.selectAllChargeByLoanId(loanId)
        }catch (e: Exception){
            settings.putString(Constants.ERROR_CODE, "CH001")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            emptyList()
        }
    }

    fun selectChargePaid(loanId: Long): ChargePaid? {
        cleanErrorData()
        return try {
            database.selectChargePaid(loanId)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "CH002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

    fun selectChargeNoPaid(loanId: Long): ChargePaid? {
        cleanErrorData()
        return try {
            database.selectChargeNoPaid(loanId)
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "CH002")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            null
        }
    }

     fun insertCharge(loanId: Long, quotaNumber: Long, chargeDate: String, customerPaymentDate: String?, quotaValue: Double, chargeValue: Double, remainingValue: Double, chargeTypeId: Long): Long{
        cleanErrorData()
        return try {
            database.insertCharge(loanId, quotaNumber, chargeDate, customerPaymentDate, quotaValue, chargeValue, remainingValue, chargeTypeId).value
        } catch (e: Exception) {
            settings.putString(Constants.ERROR_CODE, "CH004")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun updateDataCharge(loanId: Long, quotaNumber: Long, chargeDate: String, customerPaymentDate: String?, quotaValue: Double, chargeValue: Double, remainingValue: Double, chargeTypeId: Long, id: Long): Long{
        cleanErrorData()
        return try {
            database.updateDataCharge(loanId, quotaNumber, chargeDate, customerPaymentDate, quotaValue, chargeValue, remainingValue, chargeTypeId, id).value
        }catch (e: Exception){
            settings.putString(Constants.ERROR_CODE, "CH005")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }

    fun deleteCharge(id: Long): Long{
        cleanErrorData()
        return try {
            database.deleteCharge(id).value
        }catch (e: Exception){
            settings.putString(Constants.ERROR_CODE, "CH006")
            settings.putString(Constants.ERROR_MESSAGE, e.message.toString())
            0
        }
    }
}