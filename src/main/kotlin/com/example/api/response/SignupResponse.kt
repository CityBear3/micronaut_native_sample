package com.example.api.response

import io.micronaut.core.annotation.Introspected
import java.util.*

@Introspected
data class SignupResponse(val status: Int, val userID: UUID)
