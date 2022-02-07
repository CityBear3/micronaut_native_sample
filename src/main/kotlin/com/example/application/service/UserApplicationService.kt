package com.example.application.service

import com.example.domain.user.User
import com.example.domain.user.UserPassword
import com.example.domain.user.UserRepositoryExecute
import com.example.exception.PasswordRuleViolationException
import com.example.exception.UsedEmailException
import jakarta.inject.Singleton

@Singleton
class UserApplicationService(
    private val userRepositoryExecute: UserRepositoryExecute,
    private val passwordEncodeService: PasswordEncodeService
) {
    fun signup(user: User): User {
        if (userRepositoryExecute.existsByEmail(user.userEmail)) {
            throw UsedEmailException()
        }

        if (!user.userPassword.checkPassword()) {
            throw PasswordRuleViolationException()
        }

        user.userPassword = UserPassword(passwordEncodeService.encode(user.userPassword.getValue()))

        return userRepositoryExecute.insert(user)
    }
}
