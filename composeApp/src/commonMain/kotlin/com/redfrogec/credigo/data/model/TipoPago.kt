package com.redfrogec.credigo.data.model

data class TipoPago(
    val Id: Int,
    val Descripcion: String = "",
    val Dias: Int = 0,
    val Activo: Boolean = true
)