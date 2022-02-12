package com.example.exception

class EmailRuleViolationException : BaseException(ErrorCode.VALIDATION_ERROR, "email rule violation error")
