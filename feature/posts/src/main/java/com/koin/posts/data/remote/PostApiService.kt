package com.koin.posts.data.remote

import com.koin.posts.data.remote.dto.PostDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PostApiService(
    private val httpClient: HttpClient
) {
    suspend fun getPosts(): List<PostDto> {
        return httpClient.get("posts").body()
    }

    suspend fun getPostById(postId: Int): PostDto {
        return httpClient.get("posts/$postId").body()
    }
}