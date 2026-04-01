package dev.alexmester.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "claps")
data class ClapEntity(
    @PrimaryKey val articleId: Long,
    val count: Int = 0,
)
