package dev.alexmester.impl.data.repository

import dev.alexmester.database.entity.NewsArticleEntity.Companion.SOURCE_FEED
import dev.alexmester.impl.data.local.NewsFeedLocalDataSource
import dev.alexmester.impl.data.mapper.toDomain
import dev.alexmester.impl.data.mapper.toEntities
import dev.alexmester.impl.data.mapper.toFlatDomain
import dev.alexmester.impl.data.remote.NewsFeedApiService
import dev.alexmester.impl.domain.repository.NewsFeedRepository
import dev.alexmester.models.news.NewsArticle
import dev.alexmester.models.result.AppResult
import dev.alexmester.network.ext.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsFeedRepositoryImpl(
    private val remote: NewsFeedApiService,
    private val local: NewsFeedLocalDataSource,
) : NewsFeedRepository {

    override fun getArticlesFlow(): Flow<List<NewsArticle>> =
        local.getArticles().map { it.toDomain() }

    override suspend fun refreshTopNews(
        country: String,
        language: String,
        forceRefresh: Boolean,
    ): AppResult<Unit> = safeApiCall {
        val articles = remote.getTopNews(
            sourceCountry = country,
            language = language,
        ).topNews.toFlatDomain()

        if (forceRefresh) local.clearArticles()
        local.saveArticles(articles.toEntities(SOURCE_FEED))
    }

    override suspend fun loadMoreNews(
        country: String,
        language: String,
        offset: Int,
    ): AppResult<Unit> = safeApiCall {
        val articles = remote.searchNews(
            sourceCountries = country,
            language = language,
            offset = offset,
        ).news.map { it.toDomain() }

        local.saveArticles(articles.toEntities(SOURCE_FEED))
    }

    override suspend fun getLastCachedAt(): Long? =
        local.getLastCachedAt()
}