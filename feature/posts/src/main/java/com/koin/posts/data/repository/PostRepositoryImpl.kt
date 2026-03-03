package com.koin.posts.data.repository

import com.chepiga.database.dao.PostDao
import com.chepiga.database.entity.PostEntity
import com.koin.posts.data.remote.PostApiService
import com.koin.posts.domain.model.Post
import com.koin.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepositoryImpl(
    private val postApiService: PostApiService,
    private val postDao: PostDao
) : PostRepository {

    override fun getPosts(): Flow<List<Post>> {
        return postDao.getAllPosts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshPosts(): Result<Unit> {
        return try {
            val posts = postApiService.getPosts()
            val entities = posts.map { dto ->
                PostEntity(
                    id = dto.id,
                    userId = dto.userId,
                    title = dto.title,
                    body = dto.body
                )
            }
            postDao.insertPosts(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPostById(postId: Int): Post? {
        return postDao.getPostById(postId)?.toDomain()
    }

    private fun PostEntity.toDomain(): Post {
        return Post(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }
}