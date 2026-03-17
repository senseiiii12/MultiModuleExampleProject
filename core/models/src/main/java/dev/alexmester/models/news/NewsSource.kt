package dev.alexmester.models.news

data class NewsSource(
    val id: Long,
    val name: String,
    val url: String,
    val country: String?,
    val language: String?,
    val alexaRank: Int?,
)
