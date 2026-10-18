package com.redfrogec.credigo.domain.utils

import org.kotlincrypto.hash.sha2.SHA256

object PasswordUtils {
    fun hashPassword(password: String): String {
        if (password.isEmpty()) return ""
        val bytes = password.encodeToByteArray()
        val digest = SHA256().digest(bytes)
        return digest.toHexString()
    }

    fun verifyPassword(inputPassword: String, storedHash: String): Boolean {
        if (inputPassword.isEmpty() || storedHash.isEmpty()) return false
        val inputHash = hashPassword(inputPassword)
        return inputHash.equals(storedHash, ignoreCase = true)
    }
}
