package dev.alexmester.impl.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsArticleDto(
    val id: Long,
    val title: String,
    val text: String? = null,
    val summary: String? = null,
    val url: String,
    val image: String? = null,
    val video: String? = null,
    @SerialName("publish_date") val publishDate: String,
    val authors: List<String> = emptyList(),
    val category: String? = null,
    val language: String? = null,
    @SerialName("source_country") val sourceCountry: String? = null,
    val sentiment: Double? = null,
)

@Serializable
data class TopNewsResponseDto(
    @SerialName("top_news") val topNews: List<NewsClusterDto> = emptyList(),
    val language: String? = null,
    val country: String? = null,
)

@Serializable
data class NewsClusterDto(
    val news: List<NewsArticleDto> = emptyList(),
)
