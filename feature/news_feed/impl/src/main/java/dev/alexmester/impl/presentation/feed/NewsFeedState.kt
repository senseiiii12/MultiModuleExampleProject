package dev.alexmester.newsfeed.impl.presentation.feed

import dev.alexmester.models.news.NewsArticle

data class NewsFeedState(
    val articles: List<NewsArticle> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false,
    val lastCachedAt: Long? = null,
    /** true когда все страницы загружены */
    val isEndReached: Boolean = false,
    val currentOffset: Int = 0,
)
