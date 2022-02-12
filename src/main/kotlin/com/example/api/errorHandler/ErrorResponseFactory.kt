package com.example.api.errorHandler

import com.example.api.response.ErrorResponse
import com.example.exception.ErrorCode
import io.micronaut.http.HttpStatus
import jakarta.inject.Singleton

@Singleton
class ErrorResponseFactory {
    fun build(status: HttpStatus, errorCode: ErrorCode, message: String): ErrorResponse {
        return ErrorResponse(status.code, errorCode.code, message)
    }

    fun buildUnknownError(): ErrorResponse {
        return ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.code, ErrorCode.UNKNOWN_ERROR.code, "unknown error")
    }
}
