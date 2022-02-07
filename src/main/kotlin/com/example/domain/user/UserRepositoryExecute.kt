package com.example.domain.user

interface UserRepositoryExecute {
    fun insert(user: User): User

    fun findByUserEmail(userEmail: UserEmail): User

    fun existsByEmail(userEmail: UserEmail): Boolean
}
