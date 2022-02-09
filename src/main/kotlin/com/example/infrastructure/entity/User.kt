package com.example.infrastructure.entity

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@MappedEntity
data class User(
    @field:Id
    @GeneratedValue(GeneratedValue.Type.UUID)
    val id: UUID?,

    @NotNull
    val name: String,

    @NotNull
    @Email
    val email: String,

    @NotNull
    val password: String,

    @NotNull
    val role: String,

    val refreshToken: String?,

    val revoked: Boolean
) {
    constructor(name: String, email: String, password: String, role: String) : this(
        null,
        name,
        email,
        password,
        role,
        null,
        false
    )
}
