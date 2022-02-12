package com.example.exception

open class BaseException(val code: ErrorCode, override val message: String) : RuntimeException()
