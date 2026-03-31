package dev.alexmester.impl.data.remote

import dev.alexmester.impl.data.remote.dto.TopNewsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.LocalDate
import java.time.ZoneOffset

class NewsFeedApiService(private val client: HttpClient) {

    /**
     * Топ новостей по стране — используется для главной ленты.
     * [maxNewsPerCluster] — количество статей на одно событие (кластер).
     */
    suspend fun getTopNews(
        sourceCountry: String,
        language: String? = null,
        date: String? = null,
        maxNewsPerCluster: Int = 5,
    ): TopNewsResponseDto {
        val requestDate = date ?: getApiDate()

        val response = client.get("top-news") {
            parameter("source-country", sourceCountry)
            language?.let { parameter("language", it) }
            parameter("date", requestDate)
            parameter("headlines-only", false)
            parameter("max-news-per-cluster", maxNewsPerCluster)
        }.body<TopNewsResponseDto>()


        if (response.topNews.isEmpty() && date == null) {
            val yesterday = LocalDate.now(ZoneOffset.UTC).minusDays(1).toString()
            return client.get("top-news") {
                parameter("source-country", sourceCountry)
                language?.let { parameter("language", it) }
                parameter("date", yesterday)
                parameter("headlines-only", false)
                parameter("max-news-per-cluster", maxNewsPerCluster)
            }.body()
        }

        return response
    }

    private fun getApiDate(): String =
        LocalDate.now(ZoneOffset.UTC).toString()

}