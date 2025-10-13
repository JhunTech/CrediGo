package com.redfrogec.credigo.data.model

data class Interes(
    val Id: Int,
    val Valor: Double = 0.00,
    val Descripcion: String = "",
    val Activo: Boolean = true
)