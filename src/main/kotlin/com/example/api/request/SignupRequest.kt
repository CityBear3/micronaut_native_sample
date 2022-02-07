package com.example.api.request

import io.micronaut.core.annotation.Introspected

@Introspected
data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
)
