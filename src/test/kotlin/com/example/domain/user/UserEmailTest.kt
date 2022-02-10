package com.example.domain.user

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@MicronautTest
class UserEmailTest {
    lateinit var userEmail: UserEmail

    @Test
    fun testEmailValidationSuccessful() {
        userEmail = UserEmail("f-uga_h+oge.sample@example.com")
        Assertions.assertTrue(userEmail.checkEmail())
    }

    @Test
    fun testEmailValidationByNoLocal() {
        userEmail = UserEmail("sample.example.com")
        Assertions.assertFalse(userEmail.checkEmail())
    }

    @Test
    fun testEmailValidationByInvalidWord() {
        userEmail = UserEmail("#sample@example.com")
        Assertions.assertFalse(userEmail.checkEmail())
    }

    @Test
    fun testEmailValidationByFrontDot() {
        userEmail = UserEmail(".sample@example.com")
        Assertions.assertFalse(userEmail.checkEmail())
    }

    @Test
    fun testEmailValidationByEndDot() {
        userEmail = UserEmail("sample.@example.com")
        Assertions.assertFalse(userEmail.checkEmail())
    }

    @Test
    fun testEmailValidationByDuplicationDot() {
        userEmail = UserEmail("samp..le@example.com")
    }
}
