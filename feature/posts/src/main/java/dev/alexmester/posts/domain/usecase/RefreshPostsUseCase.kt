package dev.alexmester.posts.domain.usecase

import dev.alexmester.posts.domain.repository.PostRepository

class RefreshPostsUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshPosts()
    }
}