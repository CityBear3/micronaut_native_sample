package com.example.exception

class PasswordRuleViolationException : BaseException(ErrorCode.VALIDATION_ERROR, "password rule violation")
