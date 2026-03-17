package dev.alexmester.models.news

/**
 * Результат геокодирования из GET /geo-coordinates.
 * Используется для построения location-filter в GET /search-news.
 */
data class GeoCoordinates(
    val latitude: Double,
    val longitude: Double,
)