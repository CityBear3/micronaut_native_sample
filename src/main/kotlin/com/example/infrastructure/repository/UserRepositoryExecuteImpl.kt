package com.example.infrastructure.repository

import com.example.domain.user.*
import jakarta.inject.Singleton

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
            UserRole.valueOf(userEntity.role)
        )
    }

    override fun findByUserEmail(userEmail: UserEmail): User {
        val userEntity = userRepository.findByEmail(userEmail.getValue())

        return User(
            userId = userEntity.id,
            UserName(userEntity.name),
            UserEmail(userEntity.email),
            UserPassword(userEntity.password),
            UserRole.valueOf(userEntity.role)
        )
    }

    override fun existsByEmail(userEmail: UserEmail): Boolean {
        return userRepository.existsByEmail(userEmail.getValue())
    }
}
