package com.koin.users.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String? = null,
    val website: String? = null
)