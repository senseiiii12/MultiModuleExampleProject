package dev.alexmester.posts.domain.repository

import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<dev.alexmester.posts.domain.model.Post>>
    suspend fun refreshPosts(): Result<Unit>
    suspend fun getPostById(postId: Int): dev.alexmester.posts.domain.model.Post?
}