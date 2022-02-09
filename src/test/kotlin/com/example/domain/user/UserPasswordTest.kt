package com.example.domain.user

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
class UserPasswordTest {

    private lateinit var userPassword: UserPassword

    @Test
    fun testPasswordValidationSuccess() {
        userPassword = UserPassword("Abc-1234?")
        Assertions.assertTrue(userPassword.checkPassword())
    }

    @Test
    fun testPasswordValidationFailedByLowLength() {
        userPassword = UserPassword("1234")
        Assertions.assertFalse(userPassword.checkPassword())
    }

    @Test
    fun testPasswordValidationFailedByOverLength() {
        userPassword = UserPassword("01234567891011121314151617")
        Assertions.assertFalse(userPassword.checkPassword())
    }
}
