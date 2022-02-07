package com.example.domain.user

import java.util.*

data class User(
    val userId: UUID?,
    val userName: UserName,
    val userEmail: UserEmail,
    var userPassword: UserPassword,
    val userRole: UserRole
) {
    constructor(userName: UserName, userEmail: UserEmail, userPassword: UserPassword, userRole: UserRole) : this(
        null,
        userName,
        userEmail,
        userPassword,
        userRole
    )
}
