package com.example.domain.user

class UserName(private val name: String) {
    fun checkLength(): Boolean {
        if (name.length > 32) {
            return false
        }
        return true
    }

    fun getValue(): String {
        return name
    }
}
