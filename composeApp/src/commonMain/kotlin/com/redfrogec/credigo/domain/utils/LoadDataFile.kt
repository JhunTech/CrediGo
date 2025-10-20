package com.redfrogec.credigo.domain.utils

import com.redfrogec.credigo.data.model.Interes
import com.redfrogec.credigo.data.model.PreguntaSeguridad
import com.redfrogec.credigo.data.model.TipoPago
import credigo.composeapp.generated.resources.Res
import kotlinx.serialization.json.Json

suspend fun cargarListaInteres(): List<Interes>{
    val readBytes = Res.readBytes("files/Interes.json")
    val jsonString = readBytes.toString()
    return Json.decodeFromString(jsonString)
}

suspend fun cargarPreguntaSeguridad(): List<PreguntaSeguridad>{
    val readBytes = Res.readBytes("files/SecurityQuestions.json")
    val jsonString = readBytes.decodeToString()
    return Json.decodeFromString(jsonString)
}

suspend fun cargarTiposPago(): List<TipoPago>{
    val readBytes = Res.readBytes("files/TipoPago.json")
    val jsonString = readBytes.toString()
    return Json.decodeFromString(jsonString)
}