package dev.alexmester.users.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class UserApiService(
    private val httpClient: HttpClient
) {
    suspend fun getUsers(): List<dev.alexmester.users.data.remote.dto.UserDto> {
        return httpClient.get("users").body()
    }

    suspend fun getUserById(userId: Int): dev.alexmester.users.data.remote.dto.UserDto {
        return httpClient.get("users/$userId").body()
    }
}