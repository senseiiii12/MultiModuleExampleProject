package dev.alexmester.impl.domain.repository

import dev.alexmester.models.news.NewsArticle
import dev.alexmester.models.result.AppResult
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

    /**
     * Реактивный поток статей из кэша.
     * Автоматически обновляется при записи новых данных.
     */
    fun getArticlesFlow(): Flow<List<NewsArticle>>

    /**
     * Загружает топ-новости с сервера и сохраняет в кэш.
     * При pull-to-refresh [forceRefresh] = true — очищает старый кэш.
     */
    suspend fun refreshTopNews(
        country: String,
        language: String,
        forceRefresh: Boolean = false,
    ): AppResult<Unit>

    /**
     * Подгружает следующую страницу через offset.
     */
    suspend fun loadMoreNews(
        country: String,
        language: String,
        offset: Int,
    ): AppResult<Unit>

    /**
     * Время последнего обновления кэша.
     * null — кэш пустой.
     */
    suspend fun getLastCachedAt(): Long?
}