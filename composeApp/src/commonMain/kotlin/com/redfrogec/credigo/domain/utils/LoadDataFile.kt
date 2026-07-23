package com.redfrogec.credigo.domain.utils

import com.redfrogec.credigo.data.model.ChargeType
import com.redfrogec.credigo.data.model.Interest
import com.redfrogec.credigo.data.model.LoanType
import com.redfrogec.credigo.data.model.SecurityQuestion
import com.redfrogec.credigo.data.model.PaymentType
import credigo.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json

suspend fun loadInterests(): List<Interest>{
    val readBytes = Res.readBytes("files/Interest.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadSecurityQuestions(): List<SecurityQuestion>{
    val readBytes = Res.readBytes("files/SecurityQuestions.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadPaymentTypes(): List<PaymentType>{
    val readBytes = Res.readBytes("files/PaymentType.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadChargeTypes(): List<ChargeType>{
    val readBytes = Res.readBytes("files/ChargeType.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadLoanTypes(): List<LoanType>{
    val readBytes = Res.readBytes("files/LoanType.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}