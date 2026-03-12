package dev.alexmester.users.data.repository

import dev.alexmester.database.dao.UserDao
import dev.alexmester.database.entity.UserEntity
import dev.alexmester.users.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userApiService: dev.alexmester.users.data.remote.UserApiService,
    private val userDao: UserDao
) : dev.alexmester.users.domain.repository.UserRepository {

    override fun getUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshUsers(): Result<Unit> {
        return try {
            val users = userApiService.getUsers()
            val entities = users.map { dto ->
                UserEntity(
                    id = dto.id,
                    name = dto.name,
                    username = dto.username,
                    email = dto.email,
                    phone = dto.phone,
                    website = dto.website
                )
            }
            userDao.insertUsers(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)?.toDomain()
    }

    private fun UserEntity.toDomain(): User {
        return User(
            id = id,
            name = name,
            username = username,
            email = email,
            phone = phone,
            website = website
        )
    }
}