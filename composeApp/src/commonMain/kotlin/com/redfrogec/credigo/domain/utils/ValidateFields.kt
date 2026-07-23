package com.redfrogec.credigo.domain.utils

fun isValidEmail(email: String) : Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return emailRegex.matches(email)
}

fun isValidPassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$".toRegex()
    return passwordRegex.matches(password)
}

fun isValidName(name: String): Boolean {
    val nameRegex = "^[A-zÀ-ú]{3,} [A-zÀ-ú']{2,}.*$".toRegex()
    return nameRegex.matches(name)
}

fun isValidPhone(phone: String): Boolean {
    val phoneRegex = "^\\d{10}$".toRegex()
    return phoneRegex.matches(phone)
}

fun isValidIdentification(identification: String): Boolean {
    val identificationRegex = "^\\d{10,13}\$".toRegex()
    return identificationRegex.matches(identification)
}

fun isValid2Number(number: String): Boolean {
    val doubleRegex = "^\\d+\$".toRegex()
    return doubleRegex.matches(number)
}