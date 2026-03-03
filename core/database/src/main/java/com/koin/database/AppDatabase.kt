package com.chepiga.database

import com.chepiga.database.dao.PostDao
import com.chepiga.database.entity.PostEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import com.chepiga.database.dao.UserDao
import com.chepiga.database.entity.UserEntity

@Database(
    entities = [PostEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
}