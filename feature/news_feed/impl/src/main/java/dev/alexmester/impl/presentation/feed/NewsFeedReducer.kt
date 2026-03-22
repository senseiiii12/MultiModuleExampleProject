package dev.alexmester.newsfeed.impl.presentation.feed

import dev.alexmester.models.news.NewsCluster

object NewsFeedReducer {

    fun reduce(state: NewsFeedState, intent: NewsFeedIntent): NewsFeedState =
        when (intent) {
            is NewsFeedIntent.LoadFeed -> state.copy(
                isLoading = state.clusters.isEmpty(),
                error = null,
            )
            is NewsFeedIntent.Refresh -> state.copy(
                isRefreshing = true,
                error = null,
            )
            else -> state
        }

    fun onClustersLoaded(
        state: NewsFeedState,
        clusters: List<NewsCluster>,
        lastCachedAt: Long?,
    ): NewsFeedState = state.copy(
        clusters = clusters,
        isLoading = false,
        lastCachedAt = lastCachedAt,
    )

    fun onRefreshSuccess(state: NewsFeedState): NewsFeedState = state.copy(
        isRefreshing = false,
        isOffline = false,
    )

    fun onRefreshError(state: NewsFeedState, message: String): NewsFeedState = state.copy(
        isRefreshing = false,
        isLoading = false,
        error = if (state.clusters.isEmpty()) message else null,
    )

    fun onOffline(state: NewsFeedState): NewsFeedState = state.copy(
        isOffline = true,
        isRefreshing = false,
        isLoading = false,
    )
}