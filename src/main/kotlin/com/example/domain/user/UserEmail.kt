package com.example.domain.user

import javax.validation.constraints.Email

class UserEmail(@Email private val email: String) {
    fun getValue(): String {
        return email
    }
}
