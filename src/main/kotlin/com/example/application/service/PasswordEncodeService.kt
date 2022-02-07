package com.example.application.service

import jakarta.inject.Singleton
import java.security.MessageDigest
import kotlin.experimental.and


@Singleton
class PasswordEncodeService {
    fun encode(rawPassword: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-512")
        messageDigest.update("salt".encodeToByteArray())
        val bytes = messageDigest.digest(rawPassword.encodeToByteArray())
        val stringBuilder = StringBuilder()
        for (element in bytes) {
            stringBuilder.append(((element and 0xff.toByte()) + 0x100).toString(16).substring(1))
        }
        return stringBuilder.toString()
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return encode(rawPassword) == encodedPassword
    }
}
