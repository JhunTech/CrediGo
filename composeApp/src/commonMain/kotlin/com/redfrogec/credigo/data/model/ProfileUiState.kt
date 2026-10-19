package com.redfrogec.credigo.data.model

data class ProfileUiState(
    val fullName: String = "Alejandro Rafael García Ruíz",
    val displayName: String = "Alejandro García",
    val clientId: String = "#44829-L",
    val email: String = "alejandro.garcia@finanzapp.com",
    val securityQuestion: String = "¿Nombre de tu primera mascota?",
    val responseQuestion: String = "Rocky",
    val phone: String = "+52 (55) 4829 1033",
    val officialId: String = "INE-4920194857210",
    val isVerified: Boolean = true
)