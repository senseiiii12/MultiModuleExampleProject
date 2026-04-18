package dev.alexmester.impl.presentation.mvi

sealed class SearchSideEffect {
    data object NavigateBack : SearchSideEffect()
    data class NavigateToArticle(val articleId: Long, val articleUrl: String) : SearchSideEffect()
}