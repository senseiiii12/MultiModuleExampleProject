package dev.alexmester.impl.data.mapper

import dev.alexmester.database.entity.NewsArticleEntity
import dev.alexmester.impl.data.remote.dto.NewsArticleDto
import dev.alexmester.impl.data.remote.dto.NewsClusterDto
import dev.alexmester.models.news.NewsArticle
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

// ── DTO → Domain ──────────────────────────────────────────────────────────────

fun NewsArticleDto.toDomain(): NewsArticle = NewsArticle(
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

/**
 * Разворачиваем кластеры топ-новостей в плоский список статей.
 * Берём только leadArticle из каждого кластера (первую с картинкой).
 */
fun List<NewsClusterDto>.toFlatDomain(): List<NewsArticle> =
    flatMap { cluster -> cluster.news.take(1) }
        .map { it.toDomain() }

// ── Domain → Entity ───────────────────────────────────────────────────────────

fun NewsArticle.toEntity(sourceScreen: String): NewsArticleEntity = NewsArticleEntity(
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
    cachedAt = System.currentTimeMillis(),
    sourceScreen = sourceScreen,
)

fun List<NewsArticle>.toEntities(sourceScreen: String): List<NewsArticleEntity> =
    map { it.toEntity(sourceScreen) }

// ── Entity → Domain ───────────────────────────────────────────────────────────

fun NewsArticleEntity.toDomain(): NewsArticle = NewsArticle(
    id = id,
    title = title,
    text = text,
    summary = summary,
    url = url,
    image = image,
    video = video,
    publishDate = publishDate,
    authors = json.decodeFromString(authors),
    category = category,
    language = language,
    sourceCountry = sourceCountry,
    sentiment = sentiment,
)

fun List<NewsArticleEntity>.toDomain(): List<NewsArticle> = map { it.toDomain() }