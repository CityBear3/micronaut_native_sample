package com.example.domain.user

import java.time.Instant
import java.util.*

interface UserRepositoryExecute {
    fun insert(user: User): User

    fun findByUserEmail(userEmail: UserEmail): User

    fun existsByEmail(userEmail: UserEmail): Boolean

    fun findByToken(refreshToken: String): User?

    fun updateById(id: UUID, refreshToken: String, revoked: Boolean, expiredOn: Instant)
}
