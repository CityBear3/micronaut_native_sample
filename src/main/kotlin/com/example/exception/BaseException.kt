package com.example.exception

open class BaseException(val code: Int, override val message: String) : RuntimeException()
