package dev.alexmester.users.domain.usecase

import dev.alexmester.users.domain.repository.UserRepository

class RefreshUsersUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshUsers()
    }
}