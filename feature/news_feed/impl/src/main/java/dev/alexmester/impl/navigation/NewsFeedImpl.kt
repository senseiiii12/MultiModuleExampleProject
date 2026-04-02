package dev.alexmester.impl.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import dev.alexmester.api.navigation.ArticleDetailApi
import dev.alexmester.api.navigation.FeedRoute
import dev.alexmester.api.navigation.NewsFeedApi
import dev.alexmester.newsfeed.impl.presentation.feed.NewsFeedScreen
import dev.alexmester.ui.transition.SharedTransitionLocals

@OptIn(ExperimentalSharedTransitionApi::class)
class NewsFeedImpl(
    val articleDetailApi: ArticleDetailApi,
) : NewsFeedApi {

    override fun feedRoute() = FeedRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.composable<FeedRoute> {
            CompositionLocalProvider(
                SharedTransitionLocals.LocalAnimatedVisibilityScope provides this,
            ) {
                NewsFeedScreen(
                    onArticleClick = { id, url ->
                        navController.navigate(
                            articleDetailApi.articleDetailRoute(
                                articleId = id,
                                articleUrl = url
                            )
                        )
                    }
                )
            }
        }
    }
}