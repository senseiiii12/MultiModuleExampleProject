package com.koin.users.domain.repository

import com.koin.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun refreshUsers(): Result<Unit>
    suspend fun getUserById(userId: Int): User?
}