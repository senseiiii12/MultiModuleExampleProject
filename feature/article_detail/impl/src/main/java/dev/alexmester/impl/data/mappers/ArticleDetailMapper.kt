package dev.alexmester.impl.data.mappers

import dev.alexmester.database.entity.BookmarkEntity
import dev.alexmester.database.entity.NewsArticleEntity
import dev.alexmester.models.news.NewsArticle
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun NewsArticleEntity.toDomain() = NewsArticle(
    id = id, title = title, text = text, summary = summary, url = url,
    image = image, video = video, publishDate = publishDate,
    authors = json.decodeFromString(authors),
    category = category, language = language,
    sourceCountry = sourceCountry, sentiment = sentiment,
)

fun BookmarkEntity.toDomain() = NewsArticle(
    id = id, title = title, text = text, summary = summary, url = url,
    image = image, video = video, publishDate = publishDate,
    authors = json.decodeFromString(authors),
    category = category, language = language,
    sourceCountry = sourceCountry, sentiment = sentiment,
)

fun NewsArticle.toBookmarkEntity() = BookmarkEntity(
    id = id, title = title, text = text, summary = summary, url = url,
    image = image, video = video, publishDate = publishDate,
    authors = json.encodeToString(authors),
    category = category, language = language,
    sourceCountry = sourceCountry, sentiment = sentiment,
    bookmarkedAt = System.currentTimeMillis(),
)