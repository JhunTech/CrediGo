package com.redfrogec.credigo.data.model

data class Client(
    val id: Int,
    val name: String,
    val debt: String,
    val status: ClientStatus,
    val avatarUrl: String? = null // si quieres cargar imágenes reales
)

enum class ClientStatus { Active, Blocked }