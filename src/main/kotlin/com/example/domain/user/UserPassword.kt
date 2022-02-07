package com.example.domain.user

import com.google.re2j.Pattern

class UserPassword(private val password: String) {
    fun checkPassword(): Boolean {
        val pattern = Pattern.compile("""^[a-zA-Z0-9.?/-]{8,24}${'$'}""")
        if (!pattern.matcher(password.encodeToByteArray()).matches()) {
            return false
        }
        return true
    }

    fun getValue(): String {
        return password
    }
}
