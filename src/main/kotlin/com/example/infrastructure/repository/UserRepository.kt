package com.example.infrastructure.repository

import com.example.infrastructure.entity.UserEntity
import io.micronaut.context.annotation.Executable
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.time.Instant
import java.util.*

@JdbcRepository(dialect = Dialect.POSTGRES)
interface UserRepository : CrudRepository<UserEntity, UUID> {
    @Executable
    fun save(userEntity: UserEntity): UserEntity

    @Executable
    fun findByEmail(email: String): UserEntity

    @Executable
    fun findByRefreshToken(refreshToken: String): UserEntity?

    @Executable
    fun existsByEmail(email: String): Boolean

    @Executable
    fun updateById(id: UUID, refreshToken: String, revoked: Boolean, expireOn: Instant)
}
