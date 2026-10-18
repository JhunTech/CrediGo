package com.redfrogec.credigo

import com.redfrogec.credigo.domain.utils.PasswordUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class PasswordUtilsTest {

    @Test
    fun testPasswordHashingProducesExpectedHash() {
        val plainPassword = "MySecretPassword123"
        val hash = PasswordUtils.hashPassword(plainPassword)

        assertTrue(hash.isNotEmpty())
        assertNotEquals(plainPassword, hash)
        assertEquals(64, hash.length)
    }

    @Test
    fun testVerifyPasswordWithCorrectAndIncorrectPassword() {
        val password = "StrongPassword2026"
        val hash = PasswordUtils.hashPassword(password)

        assertTrue(PasswordUtils.verifyPassword(password, hash))
        assertFalse(PasswordUtils.verifyPassword("WrongPassword", hash))
        assertFalse(PasswordUtils.verifyPassword("", hash))
        assertFalse(PasswordUtils.verifyPassword(password, ""))
    }
}
