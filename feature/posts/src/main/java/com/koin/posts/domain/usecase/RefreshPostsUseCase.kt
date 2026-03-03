package com.koin.posts.domain.usecase

import com.koin.posts.domain.repository.PostRepository

class RefreshPostsUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshPosts()
    }
}