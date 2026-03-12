package dev.alexmester.posts.domain.usecase

import dev.alexmester.posts.domain.model.Post
import dev.alexmester.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<List<Post>> {
        return repository.getPosts()
    }
}