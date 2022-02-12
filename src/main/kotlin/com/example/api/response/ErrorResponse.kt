package com.example.api.response

import io.micronaut.core.annotation.Introspected

@Introspected
data class ErrorResponse(val status: Int, val code: String, val message: String)
