package dev.alexmester.api.navigation

import dev.alexmester.navigation.FeatureApi

interface ArticleDetailApi : FeatureApi {
    fun articleDetailRoute(articleId: Long, articleUrl: String): ArticleDetailRoute
}