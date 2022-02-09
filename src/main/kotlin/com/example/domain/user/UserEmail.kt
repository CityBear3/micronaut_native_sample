package com.example.domain.user

import com.google.re2j.Pattern
import javax.validation.constraints.Email

class UserEmail(@Email private val email: String) {
    fun getValue(): String {
        return email
    }

    fun checkEmail(): Boolean {
        val regex = """^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}${'$'}"""
        val pattern = Pattern.compile(regex)
        return pattern.matches(email)
    }
}
