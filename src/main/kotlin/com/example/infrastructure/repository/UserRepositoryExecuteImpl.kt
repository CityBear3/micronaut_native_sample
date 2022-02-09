package com.example.infrastructure.repository

import com.example.domain.user.*
import jakarta.inject.Singleton
import java.util.*

@Singleton
class UserRepositoryExecuteImpl(private val userRepository: UserRepository) : UserRepositoryExecute {
    override fun insert(user: User): User {
        val userEntity = userRepository.save(
            com.example.infrastructure.entity.User(
                name = user.userName.getValue(),
                email = user.userEmail.getValue(),
                password = user.userPassword.getValue(),
                role = user.userRole.toString()
            )
        )

        return User(
            userEntity.id,
            UserName(userEntity.name),
            UserEmail(userEntity.email),
            UserPassword(userEntity.password),
            UserRole.valueOf(userEntity.role),
            userEntity.refreshToken,
            userEntity.revoked
        )
    }

    override fun findByUserEmail(userEmail: UserEmail): User {
        val userEntity = userRepository.findByEmail(userEmail.getValue())

        return User(
            userId = userEntity.id,
            UserName(userEntity.name),
            UserEmail(userEntity.email),
            UserPassword(userEntity.password),
            UserRole.valueOf(userEntity.role),
            userRefreshToken = userEntity.refreshToken,
            userRevoked = userEntity.revoked
        )
    }

    override fun findByToken(refreshToken: String): User? {
        val userEntity = userRepository.findByRefreshToken(refreshToken)
        if (userEntity != null) {
            return User(
                userId = userEntity.id,
                UserName(userEntity.name),
                UserEmail(userEntity.email),
                UserPassword(userEntity.password),
                UserRole.valueOf(userEntity.role),
                userRefreshToken = userEntity.refreshToken,
                userRevoked = userEntity.revoked
            )
        }
        return null
    }

    override fun existsByEmail(userEmail: UserEmail): Boolean {
        return userRepository.existsByEmail(userEmail.getValue())
    }

    override fun updateById(id: UUID, refreshToken: String, revoked: Boolean) {
        userRepository.updateById(id, refreshToken, revoked)
    }
}
