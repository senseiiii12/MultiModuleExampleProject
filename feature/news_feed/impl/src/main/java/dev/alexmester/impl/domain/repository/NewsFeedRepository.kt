package dev.alexmester.impl.domain.repository

import dev.alexmester.models.news.NewsCluster
import dev.alexmester.models.result.AppResult
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {

    /**
     * Реактивный поток кластеров из кэша.
     * Автоматически обновляется при записи новых данных.
     */
    fun getClustersFlow(): Flow<List<NewsCluster>>

    /**
     * Загружает топ-новости с сервера и сохраняет в кэш.
     * [forceRefresh] = true при pull-to-refresh — очищает старый кэш.
     */
    suspend fun refreshTopNews(
        country: String,
        language: String,
        forceRefresh: Boolean = false,
    ): AppResult<Unit>

    suspend fun getLastCachedAt(): Long?
}