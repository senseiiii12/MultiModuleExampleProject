package dev.alexmester.impl.domain.interactor

import dev.alexmester.impl.domain.model.SearchFilters
import dev.alexmester.impl.domain.repository.SearchRepository
import dev.alexmester.models.news.NewsArticle
import dev.alexmester.models.result.AppResult
import kotlinx.coroutines.flow.Flow

class SearchInteractor(
    private val repository: SearchRepository,
) {

    fun getReadArticleIdsFlow(): Flow<List<Long>> =
        repository.getReadArticleIdsFlow()

    suspend fun search(
        query: String,
        filters: SearchFilters,
        offset: Int = 0,
    ): AppResult<List<NewsArticle>> = repository.search(
        query = query,
        filters = filters,
        offset = offset,
    )
}