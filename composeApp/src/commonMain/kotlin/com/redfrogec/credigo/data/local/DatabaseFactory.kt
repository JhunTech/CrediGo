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
import com.redfrogec.credigo.data.model.ChargePending
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
                id = userTbl.Id,
                image = userTbl.Image,
                email = userTbl.Email,
                name = userTbl.Name,
                passwordHash = userTbl.PasswordHash,
                questionId = userTbl.QuestionId,
                response = userTbl.Response,
                registerDate = LocalDateTime.parse(userTbl.RegisterDate),
                updateDate = LocalDateTime.parse(userTbl.UpdateDate.toString()),
                tokenId = userTbl.TokenId,
                token = userTbl.Token,
                tokenExpire = LocalDateTime.parse(userTbl.TokenExpire.toString())
            )
        }
        return null
    }

    fun selectDataUser(id: Long): User? {
        println("INFO: Reading the cached user from the local database...")
        val userTbl = query.selectDataUser(id).executeAsOneOrNull()
        if (userTbl != null) {
            return User(
                id = userTbl.Id,
                image = userTbl.Image,
                email = userTbl.Email,
                name = userTbl.Name,
                passwordHash = userTbl.PasswordHash,
                questionId = userTbl.QuestionId,
                response = userTbl.Response,
                registerDate = LocalDateTime.parse(userTbl.RegisterDate),
                updateDate = LocalDateTime.parse(userTbl.UpdateDate.toString()),
                tokenId = userTbl.TokenId,
                token = userTbl.Token,
                tokenExpire = LocalDateTime.parse(userTbl.TokenExpire.toString())
            )
        }
        return null
    }

    fun selectActiveUser(): User? {
        println("INFO: Reading the cached user from the local database from unique user")
        val userTbl = query.selectActiveUser().executeAsOneOrNull()
        if (userTbl != null) {
            return User(
                id = userTbl.Id,
                image = userTbl.Image,
                email = userTbl.Email,
                name = userTbl.Name,
                passwordHash = userTbl.PasswordHash,
                questionId = userTbl.QuestionId,
                response = userTbl.Response,
                registerDate = LocalDateTime.parse(userTbl.RegisterDate),
                updateDate = LocalDateTime.parse(userTbl.UpdateDate.toString()),
                tokenId = userTbl.TokenId,
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

    fun updatePassword(passwordHash: String, id: Long): QueryResult<Long>  {
        return query.updatePassword(passwordHash, id)
    }

    fun recoveryPasswordUserByEmailAndQuestion(passwordHash: String, email: String, questionId: Long, response: String): QueryResult<Long>  {
        return query.recoveryPasswordUserByEmailAndQuestion(passwordHash, email, questionId, response)
    }

    fun deleteUser(id: Long): QueryResult<Long> {
        return query.deleteUser(id)
    }

    fun deleteUserData(id: Long): QueryResult<Long> {
        return query.deleteUserData(id)
    }

    fun selectAllKey(): String{
        return query.selectAllKey().executeAsOneOrNull()?.PublicKey.toString()
    }

    fun insertKey(key: String): QueryResult<Long> {
        return query.insertKey(KeyTbl(0, key))
    }

    fun deleteKey(): QueryResult<Long> {
        return query.deleteKey()
    }

    fun selectAllClients(userId: Long): List<Client> {
        val blocked: Long = 1
        return query.selectAllClients(userId).executeAsList().map {
            Client(
                id = it.Id,
                userId = it.UserId,
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

    fun selectActiveClients(userId: Long): List<Client> {
        val blocked: Long = 1
        return query.selectActiveClients(userId).executeAsList().map {
            Client(
                id = it.Id,
                userId = it.UserId,
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

    fun selectClientById(id: Long): Client? {
        val blocked: Long = 1
        val clientTbl = query.selectClientById(id).executeAsOneOrNull()
        if (clientTbl != null) {
            return Client(
                id = clientTbl.Id,
                userId = clientTbl.UserId,
                image = clientTbl.Image.toString(),
                identification = clientTbl.Identification,
                name = clientTbl.Name,
                email = clientTbl.Email.toString(),
                phone = clientTbl.Phone.toString(),
                address = clientTbl.Address.toString(),
                blocked = clientTbl.Blocked == blocked,
                registerDate = LocalDateTime.parse(clientTbl.RegisterDate.toString())
            )
        }
        return null
    }

    fun insertClient(userId: Long, image: String?, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime): QueryResult<Long> {
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
        return query.insertClient(client)
    }

    fun updateDataClient(userId: Long, image: String?, identification: String, name: String, email: String?, phone: String?, address: String?, blocked: Boolean, registerDate: LocalDateTime, id: Long): QueryResult<Long> {
        return query.updateDataClient(userId, image, identification, name, email, phone, address, if (blocked) 1 else 0, registerDate.toString(), id)
    }

    fun deleteClient(id: Long): QueryResult<Long> {
        return query.deleteClient(id)
    }

    fun selectAllLoansByClientId(clientId: Long, active: Boolean): List<Loan> {
        val activeRequest: Long = if (active) 1 else 0
        val activeResponse: Long = 1
        return query.selectAllLoansByClientId(clientId, activeRequest).executeAsList().map {
            Loan(
                id = it.Id,
                clientId = it.ClientId,
                clientName = "",
                value = it.Value,
                paymentTypeId = it.PaymentTypeId.toInt(),
                interestId = it.InterestId.toInt(),
                quotaNumbers = it.QuotaNumbers.toInt(),
                active = it.Active == activeResponse,
                creationDate = it.CreationDate,
                deliveryDate = it.DeliveryDate,
                loanTypeId = it.LoanTypeId.toInt()
            )
        }
    }

    fun selectAllLoansByUserId(userId: Long, active: Boolean): List<Loan> {
        val activeRequest: Long = if (active) 1 else 0
        val activeResponse: Long = 1
        return query.selectAllLoansByUserId(userId, activeRequest).executeAsList().map {
            Loan(
                id = it.Id,
                clientId = it.ClientId,
                clientName = it.ClientName,
                value = it.Value,
                paymentTypeId = it.PaymentTypeId.toInt(),
                interestId = it.InterestId.toInt(),
                quotaNumbers = it.QuotaNumbers.toInt(),
                active = it.Active == activeResponse,
                creationDate = it.CreationDate,
                deliveryDate = it.DeliveryDate,
                loanTypeId = it.LoanTypeId.toInt()
            )
        }
    }

    fun selectLoanById(id: Long): Loan? {
        val activeResponse: Long = 1
        val loanTbl = query.selectLoanById(id).executeAsOneOrNull()
        if (loanTbl != null) {
            return Loan(
                id = loanTbl.Id,
                clientId = loanTbl.ClientId,
                clientName = "",
                value = loanTbl.Value,
                paymentTypeId = loanTbl.PaymentTypeId.toInt(),
                interestId = loanTbl.InterestId.toInt(),
                quotaNumbers = loanTbl.QuotaNumbers.toInt(),
                active = loanTbl.Active == activeResponse,
                creationDate = loanTbl.CreationDate,
                deliveryDate = loanTbl.DeliveryDate,
                loanTypeId = loanTbl.LoanTypeId.toInt()
            )
        }
        return null
    }

    fun insertLoan(clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String, loanTypeId: Long): QueryResult<Long> {
        val loan = LoanTbl(
            0,
            clientId,
            value,
            paymentTypeId,
            interestId,
            quotaNumbers,
            if (active) 1 else 0,
            creationDate,
            deliveryDate,
            loanTypeId
        )
        return query.insertLoan(loan)
    }

    fun updateLoan(clientId: Long, value: Double, paymentTypeId: Long, interestId: Long, quotaNumbers: Long, active: Boolean, creationDate: String, deliveryDate: String, loanTypeId: Long, id: Long): QueryResult<Long>{
        return query.updateLoan(clientId, value, paymentTypeId, interestId, quotaNumbers, if (active) 1 else 0, creationDate, deliveryDate,loanTypeId, id)
    }

    fun updateLoanState(active: Boolean, id: Long): QueryResult<Long>{
        return query.updateLoanState(if (active) 1 else 0, id)
    }

    fun deleteLoan(id: Long): QueryResult<Long> {
        return query.deleteLoan(id)
    }

    fun selectAllChargeByLoanId(loanId: Long): List<Charge> {
        return query.selectAllChargeByLoanId(loanId).executeAsList().map {
            Charge(
                id = it.Id,
                loanId = it.LoanId,
                quotaNumber = it.QuotaNumber.toInt(),
                chargeDate = it.ChargeDate,
                customerPaymentDate = it.CustomerPaymentDate,
                quotaValue = it.QuotaValue,
                chargeValue = it.ChargeValue,
                remainingValue = it.RemainingValue,
                chargeTypeId = it.ChargeTypeId.toInt()
            )
        }
    }

    fun selectChargesTotals(loanId: Long): ChargePaid {
        val chargePaid = query.selectChargesTotals(loanId).executeAsOne()
        return ChargePaid(
            loanId = chargePaid.LoanId,
            totalFeesPaid = chargePaid.TotalFeesPaid.toInt(),
            totalAmountCharged = chargePaid.TotalAmountCharged.toString().toDouble(),
            totalAmountRemaining = chargePaid.TotalAmountRemaining.toString().toDouble(),
            totalAmountQuota = chargePaid.TotalAmountQuota.toString().toDouble()
        )
    }

    fun selectChargePaid(loanId: Long): ChargePaid {
        val chargePaid = query.selectChargesPaid(loanId).executeAsOne()
        return ChargePaid(
            loanId = chargePaid.LoanId,
            totalFeesPaid = chargePaid.TotalFeesPaid.toInt(),
            totalAmountCharged = chargePaid.TotalAmountCharged.toString().toDouble(),
            totalAmountRemaining = chargePaid.TotalAmountRemaining.toString().toDouble(),
            totalAmountQuota = chargePaid.TotalAmountQuota.toString().toDouble()
        )
    }

    fun selectChargeNoPaid(loanId: Long): ChargePaid  {
        val chargeNoPaid = query.selectChargesNoPaid(loanId).executeAsOne()
        return ChargePaid(
            loanId = chargeNoPaid.LoanId,
            totalFeesPaid = chargeNoPaid.TotalFeesPaid.toInt(),
            totalAmountCharged = chargeNoPaid.TotalAmountCharged.toString().toDouble(),
            totalAmountRemaining = chargeNoPaid.TotalAmountRemaining.toString().toDouble(),
            totalAmountQuota = chargeNoPaid.TotalAmountQuota.toString().toDouble()
        )
    }

    fun nextPendingQuotaByLoan(loanId: Long): ChargePending {
        val chargePending = query.nextPendingQuotaByLoan(loanId).executeAsOne()
        return ChargePending(
            id = chargePending.Id,
            chargeDate = chargePending.ChargeDate,
            quotaValue = chargePending.QuotaValue,
            chargeValue = chargePending.ChargeValue,
            remainingValue = chargePending.RemainingValue
        )
    }

    fun insertCharge(loanId: Long, quotaNumber: Long, chargeDate: String, customerPaymentDate: String?, quotaValue: Double, chargeValue: Double, remainingValue: Double, chargeTypeId: Long): QueryResult<Long> {
        val charge = ChargeTbl(
            0,
            loanId,
            quotaNumber,
            chargeDate,
            customerPaymentDate,
            quotaValue,
            chargeValue,
            remainingValue,
            chargeTypeId
        )
        return query.insertCharge(charge)
    }

    fun lastInsertId(): Long {
        return query.lastInsertId().executeAsOne()
    }

    fun updateDataCharge(loanId: Long, customerPaymentDate: String?, quotaValue: Double, chargeValue: Double, remainingValue: Double, id: Long): QueryResult<Long> {
        return query.updateDataCharge(loanId, customerPaymentDate, quotaValue, chargeValue, remainingValue, id)
    }

    fun deleteCharge(id: Long): QueryResult<Long> {
        return query.deleteCharge(id)
    }

    fun deleteAllCharges(id: Long): QueryResult<Long> {
        return query.deleteAllCharges(id)
    }
}