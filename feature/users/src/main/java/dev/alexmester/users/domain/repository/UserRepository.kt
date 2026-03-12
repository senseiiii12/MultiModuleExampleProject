package dev.alexmester.users.domain.repository

import dev.alexmester.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun refreshUsers(): Result<Unit>
    suspend fun getUserById(userId: Int): User?
}