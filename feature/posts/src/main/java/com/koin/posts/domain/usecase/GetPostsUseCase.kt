package com.koin.posts.domain.usecase

import com.koin.posts.domain.model.Post
import com.koin.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<List<Post>> {
        return repository.getPosts()
    }
}