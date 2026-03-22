package dev.alexmester.newsfeed.impl.presentation.feed

sealed class NewsFeedIntent {
    /** Первичная загрузка при открытии экрана */
    data object LoadFeed : NewsFeedIntent()
    /** Pull-to-refresh */
    data object Refresh : NewsFeedIntent()
    /** Подгрузка следующей страницы */
    data object LoadMore : NewsFeedIntent()
    /** Тап на статью */
    data class ArticleClick(val articleId: Long, val articleUrl: String) : NewsFeedIntent()
    /** Тап на поиск */
    data object SearchClick : NewsFeedIntent()
}
