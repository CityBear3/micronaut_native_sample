package com.example.domain.user

import com.google.re2j.Pattern

class UserPassword(private val password: String) {
    fun checkPassword(): Boolean {
        val pattern = Pattern.compile("""^[a-zA-Z0-9.?/-]{8,24}${'$'}""")
        return pattern.matches(password)
    }

    fun getValue(): String {
        return password
    }
}
