package dev.alexmester.models.news

/**
 * Кластер новостей об одном событии из GET /top-news.
 *
 * API возвращает массив кластеров, каждый из которых содержит несколько
 * статей об одном и том же событии из разных источников.
 */
data class NewsCluster(
    val id: Int,
    val articles: List<NewsArticle>,
) {
    /** Ведущая статья кластера (первая с изображением или просто первая) */
    val leadArticle: NewsArticle
        get() = articles.firstOrNull { it.image != null } ?: articles.first()
}