package com.example.infrastructure.repository

import com.example.infrastructure.entity.User
import io.micronaut.context.annotation.Executable
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface UserRepository : CrudRepository<User, UUID> {
    @Executable
    fun save(user: User): User

    @Executable
    fun findByEmail(email: String): User

    @Executable
    fun findByRefreshToken(refreshToken: String): User?

    @Executable
    fun existsByEmail(email: String): Boolean

    @Executable
    fun updateById(id: UUID, refreshToken: String, revoked: Boolean)
}
