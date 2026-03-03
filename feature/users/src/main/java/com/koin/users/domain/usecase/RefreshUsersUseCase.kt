package com.koin.users.domain.usecase

import com.koin.users.domain.repository.UserRepository

class RefreshUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshUsers()
    }
}