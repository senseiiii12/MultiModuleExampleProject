package com.chepiga.database.dao

import androidx.room.*
import com.chepiga.database.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPostById(postId: Int): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("DELETE FROM posts")
    suspend fun clearPosts()
}