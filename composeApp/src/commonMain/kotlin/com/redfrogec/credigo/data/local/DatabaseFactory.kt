package com.redfrogec.credigo.data.local

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import com.redfrogec.ChargeTbl
import com.redfrogec.ClientTbl
import com.redfrogec.CrediBase
import com.redfrogec.KeyTbl
import com.redfrogec.LoanTbl
import com.redfrogec.UserTbl
import com.redfrogec.credigo.data.model.Charge
import com.redfrogec.credigo.data.model.ChargePaid
import com.redfrogec.credigo.data.model.Client
import com.redfrogec.credigo.data.model.Loan
import com.redfrogec.credigo.data.model.User
import kotlinx.datetime.LocalDateTime

interface DatabaseDriverFactory{
    fun createDriver(): SqlDriver
}

class LocalDatabase(
    databaseDriverFactory: DatabaseDriverFactory
) {
    private val database = CrediBase(
        databaseDriverFactory.createDriver()
    )
    private val query = database.crediBaseQueries

    fun findUserByEmailAndPassword(email: String, password: String): User? {
        println("INFO: Reading the cached user from the local database...")
        val userTbl = query.findUserByEmailAndPassword(email, password).executeAsOneOrNull()
        if (userTbl != null) {
            return User(
                id = userTbl.Id.toInt(),
                imagen = userTbl.Image,
                email = userTbl.Email,
                name = userTbl.Name,
                passwordHash = userTbl.PasswordHash,
                questionId = userTbl.QuestionId.toInt(),
                response = userTbl.Response,
                registerDate = LocalDateTime.parse(userTbl.RegisterDate),
                updateDate = LocalDateTime.parse(userTbl.UpdateDate.toString()),
                tokenId = userTbl.TokenId?.toInt(),
                token = userTbl.Token,
                tokenExpire = LocalDateTime.parse(userTbl.TokenExpire.toString())
            )
        }
        return null
    }

    fun insertUser(image: String?, email: String, name: String, passwordHash: String, questionId: Long, response: String, registerDate: LocalDateTime, updateDate: LocalDateTime?, tokenId: Long?, token: String?, tokenExpire: LocalDateTime?): QueryResult<Long> {
        val user = UserTbl(
            0,
            image,
            email,
            name,
            passwordHash,
            questionId,
            response,
            registerDate.toString(),
            updateDate.toString(),
            tokenId,
            token,
            tokenExpire.toString()
        )
        return query.insertUser(user)
    }

    fun updateUser(image: String?, email: String, name: String, passwordHash: String, questionId: Long, response: String, registerDate: LocalDateTime, updateDate: LocalDateTime?, tokenId: Long?, token: String?, tokenExpire: LocalDateTime?, id: Long): QueryResult<Long>  {
        return query.updateUser(image, email, name, passwordHash, questionId, response, registerDate.toString(), updateDate.toString(), tokenId, token, tokenExpire.toString(), id)
    }

    fun recoveryPasswordUserByEmailAndQuestion(passwordHash: String, email: String, questionId: Long, response: String): QueryResult<Long>  {
        return query.recoveryPasswordUserByEmailAndQuestion(passwordHash, email, questionId, response)
    }

    fun deleteUser(id: Long): QueryResult<Long>  {
        return query.deleteUser(id)
    }

    fun selectAllKey(): String{
        return query.selectAllKey().executeAsOneOrNull()?.PublicKey.toString()
    }

    fun insertKey(key: String){
        query.insertKey(KeyTbl(0, key))
    }

    fun deleteKey(){
        query.deleteKey()
    }

    fun selectAllClients(userId: Long): List<Client> {
        val blocked: Long = 1
        return query.selectAllClients(userId).executeAsList().map {
            Client(
                id = it.Id.toInt(),
                userId = it.UserId.toInt(),
                image = it.Image.toString(),
                identification = it.Identification,
                name = it.Name,
                email = it.Email.toString(),
                phone = it.Phone.toString(),
                address = it.Address.toString(),
                blocked = it.Blocked == blocked,
                registerDate = LocalDateTime.parse(it.RegisterDate.toString())
            )
        }
    }

    fun insertClient(userId: Long, image: String?, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime) {
        val client = ClientTbl(
            0,
            userId,
            image,
            identification,
            name,
            email,
            phone,
            address,
            if (blocked) 1 else 0,
            registerDate.toString()
        )
        query.insertClient(client)
    }

    fun updateDataClient(userId: Long, image: String?, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime, id: Long) {
        query.updateDataClient(userId, image, identification, name, email, phone, address, if (blocked) 1 else 0, registerDate.toString(), id)
    }

    fun deleteClient(id: Long) {
        query.deleteClient(id)
    }

    fun selectAllLoansByClientId(clientId: Long): List<Loan> {
        val active: Long = 1
        return query.selectAllLoansByClientId(clientId).executeAsList().map {
            Loan(
                id = it.Id.toString(),
                clientId = it.ClientId.toString(),
                clientName = "",
                value = it.Value.toDouble(),
                paymentTypeId = it.PaymentTypeId.toInt(),
                interestId = it.InterestId.toInt(),
                quotaNumbers = it.QuotaNumbers.toInt(),
                active = it.Active == active,
                creationDate = it.CreationDate,
                deliveryDate = it.DeliveryDate
            )
        }
    }

    fun selectAllLoansByUserId(userId: Long): List<Loan> {
        val active: Long = 1
        return query.selectAllLoansByUserId(userId).executeAsList().map {
            Loan(
                id = it.Id.toString(),
                clientId = it.ClientId.toString(),
                clientName = it.ClientName,
                value = it.Value.toDouble(),
                paymentTypeId = it.PaymentTypeId.toInt(),
                interestId = it.InterestId.toInt(),
                quotaNumbers = it.QuotaNumbers.toInt(),
                active = it.Active == active,
                creationDate = it.CreationDate,
                deliveryDate = it.DeliveryDate
            )
        }
    }

    fun insertLoan(clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String){
        val loan = LoanTbl(
            0,
            clientId,
            value,
            paymentTypeId,
            interestId,
            quotaNumbers,
            if (active) 1 else 0,
            creationDate,
            deliveryDate
        )
        query.insertLoan(loan)
    }

    fun updateDataLoan(clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String, id: Long){
        query.updateDataLoan(clientId, value, paymentTypeId, interestId, quotaNumbers, if (active) 1 else 0, creationDate, deliveryDate, id)
    }

    fun deleteLoan(id: Long){
        query.deleteLoan(id)
    }

    fun selectAllChargeByLoanId(loanId: Long): List<Charge> {
        return query.selectAllChargeByLoanId(loanId).executeAsList().map {
            Charge(
                id = it.Id.toString(),
                loanId = it.LoanId.toString(),
                quotaNumber = it.QuotaNumber.toInt(),
                chargeDate = it.ChargeDate,
                customerPaymentDate = it.CustomerPaymentDate,
                quotaValue = it.QuotaValue.toDouble(),
                chargeValue = it.ChargeValue.toDouble(),
                remainingValue = it.RemainingValue.toDouble()
            )
        }
    }

    fun selectChargePaid(loanId: Long): ChargePaid {
        val chargePaid = query.selectChargePaid(loanId).executeAsOne()
        return ChargePaid(
            loanId = chargePaid.LoanId.toString(),
            totalFeesPaid = chargePaid.TotalFeesPaid.toInt(),
            totalAmountCharged = chargePaid.TotalAmountCharged.toString().toDouble()
        )
    }

    fun insertCharge(loanId: Long, quotaNumber: Long, chargeDate: String, customerPaymentDate: String?, quotaValue: Double, chargeValue: Double, remainingValue: Double){
        val charge = ChargeTbl(
            0,
            loanId,
            quotaNumber,
            chargeDate,
            customerPaymentDate,
            quotaValue,
            chargeValue,
            remainingValue
        )
        query.insertCharge(charge)
    }

    fun updateDataCharge(loanId: Long, quotaNumber: Long, chargeDate: String, customerPaymentDate: String?, quotaValue: Double, chargeValue: Double, remainingValue: Double, id: Long){
        query.updateDataCharge(loanId, quotaNumber, chargeDate, customerPaymentDate, quotaValue, chargeValue, remainingValue, id)
    }

    fun deleteCharge(id: Long){
        query.deleteCharge(id)
    }
}