package com.example.exception

class MissingRequiredParameterException :
    BaseException(ErrorCode.LACK_OF_PARAMETER_ERROR, "missing required elements error")
