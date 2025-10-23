package com.redfrogec.credigo.domain.utils

import com.redfrogec.credigo.data.model.Interes
import com.redfrogec.credigo.data.model.PreguntaSeguridad
import com.redfrogec.credigo.data.model.TipoPago
import credigo.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json

suspend fun loadInterests(): List<Interes>{
    val readBytes = Res.readBytes("files/Interest.json")
    val jsonString = readBytes.toString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadSecurityQuestions(): List<PreguntaSeguridad>{
    val readBytes = Res.readBytes("files/SecurityQuestions.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}

suspend fun loadPaymentTypes(): List<TipoPago>{
    val readBytes = Res.readBytes("files/PaymentType.json")
    val jsonString = readBytes.toString()
    return Json.decodeFromString(jsonString)
}