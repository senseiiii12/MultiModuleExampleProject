package dev.alexmester.posts.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PostApiService(
    private val httpClient: HttpClient
) {
    suspend fun getPosts(): List<dev.alexmester.posts.data.remote.dto.PostDto> {
        return httpClient.get("posts").body()
    }

    suspend fun getPostById(postId: Int): dev.alexmester.posts.data.remote.dto.PostDto {
        return httpClient.get("posts/$postId").body()
    }
}