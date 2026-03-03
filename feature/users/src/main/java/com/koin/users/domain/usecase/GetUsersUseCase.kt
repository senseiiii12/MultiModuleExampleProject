package com.koin.users.domain.usecase

import com.koin.users.domain.model.User
import com.koin.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getUsers()
    }
}