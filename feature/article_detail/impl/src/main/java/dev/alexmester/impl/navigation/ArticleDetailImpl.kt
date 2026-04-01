package dev.alexmester.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.alexmester.api.navigation.ArticleDetailApi
import dev.alexmester.api.navigation.ArticleDetailRoute
import dev.alexmester.impl.presentation.ArticleDetailScreen

class ArticleDetailImpl : ArticleDetailApi {

    override fun articleDetailRoute(articleId: Long, articleUrl: String) =
        ArticleDetailRoute(articleId = articleId, articleUrl = articleUrl)

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.composable<ArticleDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<ArticleDetailRoute>()
            ArticleDetailScreen(
                articleId = route.articleId,
                articleUrl = route.articleUrl,
                onBack = { navController.navigateUp() },
            )
        }
    }
}