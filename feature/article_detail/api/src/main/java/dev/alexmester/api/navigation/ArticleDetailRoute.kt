package dev.alexmester.api.navigation

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDetailRoute(
    val articleId: Long,
    val articleUrl: String,
)