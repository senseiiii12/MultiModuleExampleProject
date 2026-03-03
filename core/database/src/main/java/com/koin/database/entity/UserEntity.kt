package com.chepiga.database.entity

import androidx.room.*

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String?,
    val website: String?
)
