package dev.alexmester.newsfeed.impl.presentation.feed

import dev.alexmester.models.news.NewsArticle
import dev.alexmester.models.news.NewsCluster

data class NewsFeedState(
    val clusters: List<NewsCluster> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false,
    val lastCachedAt: Long? = null,
)
