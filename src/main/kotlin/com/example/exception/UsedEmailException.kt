package com.example.exception

class UsedEmailException : BaseException(ErrorCode.VALIDATION_ERROR, "this email has already been used")
