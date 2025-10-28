package com.redfrogec.credigo.domain.utils

import com.redfrogec.credigo.data.model.Interest
import com.redfrogec.credigo.data.model.SecurityQuestion
import com.redfrogec.credigo.data.model.PaymentType
import credigo.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json

suspend fun loadInterests(): List<Interest>{
    val readBytes = Res.readBytes("files/Interest.json")
    val jsonString = readBytes.toString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadSecurityQuestions(): List<SecurityQuestion>{
    val readBytes = Res.readBytes("files/SecurityQuestions.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadPaymentTypes(): List<PaymentType>{
    val readBytes = Res.readBytes("files/PaymentType.json")
    val jsonString = readBytes.toString()
    return Json.decodeFromString(jsonString)
}