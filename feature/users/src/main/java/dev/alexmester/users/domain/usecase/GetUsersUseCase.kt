package dev.alexmester.users.domain.usecase

import dev.alexmester.users.domain.model.User
import dev.alexmester.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getUsers()
    }
}