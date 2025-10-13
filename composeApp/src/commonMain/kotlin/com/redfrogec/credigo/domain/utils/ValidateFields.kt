package com.redfrogec.credigo.domain.utils

fun isValidEmail(email: String) : Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(email)
}
fun isValidPassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"
    return passwordRegex.toRegex().matches(password)
}