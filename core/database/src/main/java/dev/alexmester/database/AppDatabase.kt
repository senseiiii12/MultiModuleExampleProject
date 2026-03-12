package dev.alexmester.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.alexmester.database.dao.PostDao
import dev.alexmester.database.dao.UserDao
import dev.alexmester.database.entity.PostEntity
import dev.alexmester.database.entity.UserEntity


@Database(
    entities = [PostEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
}