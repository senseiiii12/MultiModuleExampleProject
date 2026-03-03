package com.koin.users.data.remote

import com.koin.users.data.remote.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserApiService(
    private val httpClient: HttpClient
) {
    suspend fun getUsers(): List<UserDto> {
        return httpClient.get("users").body()
    }

    suspend fun getUserById(userId: Int): UserDto {
        return httpClient.get("users/$userId").body()
    }
}