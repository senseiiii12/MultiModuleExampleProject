package dev.alexmester.models.news

data class NewsArticle(
    val id: Long,
    val title: String,
    val text: String?,
    val summary: String?,
    val url: String,
    val image: String?,
    val video: String?,
    val publishDate: String,
    val authors: List<String>,
    val category: String?,
    val language: String?,
    val sourceCountry: String?,
    val sentiment: Double?,
)
