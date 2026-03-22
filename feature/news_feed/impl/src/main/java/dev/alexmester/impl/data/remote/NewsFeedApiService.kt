package dev.alexmester.impl.data.remote

import dev.alexmester.impl.data.remote.dto.SearchNewsResponseDto
import dev.alexmester.impl.data.remote.dto.TopNewsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NewsFeedApiService(private val client: HttpClient) {

    /**
     * Топ новостей по стране — используется для главной ленты.
     */
    suspend fun getTopNews(
        sourceCountry: String,
        language: String? = null,
        date: String? = null,
    ): TopNewsResponseDto = client.get("top-news") {
        parameter("source-country", sourceCountry)
        parameter("language", language)
        parameter("date", date)
        parameter("headlines-only", false)
    }.body()

    /**
     * Поиск новостей с пагинацией — используется для подгрузки
     * дополнительных статей через offset.
     */
    suspend fun searchNews(
        sourceCountries: String? = null,
        language: String? = null,
        offset: Int = 0,
        number: Int = 20,
    ): SearchNewsResponseDto = client.get("search-news") {
        parameter("source-countries", sourceCountries)
        parameter("language", language)
        parameter("sort", "publish-time")
        parameter("offset", offset)
        parameter("number", number)
    }.body()
}