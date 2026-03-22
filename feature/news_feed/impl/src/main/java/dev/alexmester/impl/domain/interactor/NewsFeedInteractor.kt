package dev.alexmester.impl.domain.interactor

import dev.alexmester.datastore.UserPreferencesDataSource
import dev.alexmester.impl.domain.repository.NewsFeedRepository
import dev.alexmester.models.news.NewsArticle
import dev.alexmester.models.result.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NewsFeedInteractor(
    private val repository: NewsFeedRepository,
    private val preferencesDataSource: UserPreferencesDataSource,
) {

    fun getArticlesFlow(): Flow<List<NewsArticle>> =
        repository.getArticlesFlow()

    suspend fun refresh(forceRefresh: Boolean = false): AppResult<Unit> {
        val prefs = preferencesDataSource.userPreferences.first()
        return repository.refreshTopNews(
            country = prefs.defaultCountry,
            language = prefs.defaultLanguage,
            forceRefresh = forceRefresh,
        )
    }

    suspend fun loadMore(offset: Int): AppResult<Unit> {
        val prefs = preferencesDataSource.userPreferences.first()
        return repository.loadMoreNews(
            country = prefs.defaultCountry,
            language = prefs.defaultLanguage,
            offset = offset,
        )
    }

    suspend fun getLastCachedAt(): Long? =
        repository.getLastCachedAt()
}
