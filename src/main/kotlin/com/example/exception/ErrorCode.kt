package com.example.exception

enum class ErrorCode(val code: String) {
    VALIDATION_ERROR("validation_error"),
    LACK_OF_PARAMETER_ERROR("lack_of_parameter_error"),
    UNKNOWN_ERROR("unknown_error")
}
