package com.example.api.response

import io.micronaut.core.annotation.Introspected

@Introspected
data class ErrorResponse(val code: Int, val message: String)
