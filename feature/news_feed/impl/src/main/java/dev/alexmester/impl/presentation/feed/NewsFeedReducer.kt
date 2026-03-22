package dev.alexmester.newsfeed.impl.presentation.feed

import dev.alexmester.models.news.NewsArticle

object NewsFeedReducer {

    fun reduce(state: NewsFeedState, intent: NewsFeedIntent): NewsFeedState =
        when (intent) {
            is NewsFeedIntent.LoadFeed -> state.copy(
                isLoading = state.articles.isEmpty(),
                error = null,
            )
            is NewsFeedIntent.Refresh -> state.copy(
                isRefreshing = true,
                error = null,
            )
            is NewsFeedIntent.LoadMore -> state.copy(
                isLoadingMore = true,
            )
            else -> state
        }

    fun onArticlesLoaded(
        state: NewsFeedState,
        articles: List<NewsArticle>,
        lastCachedAt: Long?,
    ): NewsFeedState = state.copy(
        articles = articles,
        isLoading = false,
        lastCachedAt = lastCachedAt,
    )

    fun onRefreshSuccess(state: NewsFeedState): NewsFeedState = state.copy(
        isRefreshing = false,
        isOffline = false,
        currentOffset = 0,
        isEndReached = false,
    )

    fun onRefreshError(state: NewsFeedState, message: String): NewsFeedState = state.copy(
        isRefreshing = false,
        isLoading = false,
        error = if (state.articles.isEmpty()) message else null,
    )

    fun onLoadMoreSuccess(
        state: NewsFeedState,
        newOffset: Int,
        isEndReached: Boolean,
    ): NewsFeedState = state.copy(
        isLoadingMore = false,
        currentOffset = newOffset,
        isEndReached = isEndReached,
    )

    fun onLoadMoreError(state: NewsFeedState): NewsFeedState = state.copy(
        isLoadingMore = false,
    )

    fun onOffline(state: NewsFeedState): NewsFeedState = state.copy(
        isOffline = true,
        isRefreshing = false,
        isLoading = false,
    )
}
