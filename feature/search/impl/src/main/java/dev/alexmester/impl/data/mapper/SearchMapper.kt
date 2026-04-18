package dev.alexmester.impl.data.mapper

import dev.alexmester.database.entity.ArticleEntity
import dev.alexmester.impl.data.remote.dto.SearchArticleDto
import dev.alexmester.models.news.NewsArticle
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun SearchArticleDto.toDomain(): NewsArticle = NewsArticle(
    id = id,
    title = title,
    text = text,
    summary = summary,
    url = url,
    image = image,
    video = video,
    publishDate = publishDate,
    authors = authors,
    category = category,
    language = language,
    sourceCountry = sourceCountry,
    sentiment = sentiment,
)

fun SearchArticleDto.toEntity(): ArticleEntity = ArticleEntity(
    id = id,
    title = title,
    text = text,
    summary = summary,
    url = url,
    image = image,
    video = video,
    publishDate = publishDate,
    authors = json.encodeToString(authors),
    category = category,
    language = language,
    sourceCountry = sourceCountry,
    sentiment = sentiment,
)