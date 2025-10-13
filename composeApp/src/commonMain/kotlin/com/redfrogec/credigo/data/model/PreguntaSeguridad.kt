package com.redfrogec.credigo.data.model

import kotlinx.datetime.LocalDateTime

data class PreguntaSeguridad(
    val Id: Int,
    val Descripcion: String = "",
    val FechaCreacion: LocalDateTime = LocalDateTime(2025, 10, 6, 0, 0, 0),
    val Activo: Boolean = true
)