package dev.alexmester.impl.presentation

import dev.alexmester.models.news.NewsArticle

sealed interface ArticleDetailScreenState {
    data object Loading : ArticleDetailScreenState
    data class Error(val message: String) : ArticleDetailScreenState
    data class Content(
        val article: NewsArticle,
        val isBookmarked: Boolean = false,
        val clapCount: Int = 0,
        val isClapAnimating: Boolean = false,
    ) : ArticleDetailScreenState
}