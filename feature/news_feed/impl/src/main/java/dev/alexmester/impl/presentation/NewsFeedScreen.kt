package dev.alexmester.newsfeed.impl.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.alexmester.newsfeed.impl.presentation.components.ClusterHeader
import dev.alexmester.newsfeed.impl.presentation.components.NewsArticleCard
import dev.alexmester.newsfeed.impl.presentation.components.OfflineBanner
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTypography
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedScreen(
    navController: NavHostController,
    viewModel: NewsFeedViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is NewsFeedSideEffect.NavigateToArticle -> {
                    // будет подключено когда реализуем feature:article-detail
                }
                is NewsFeedSideEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lask",
                        style = MaterialTheme.LaskTypography.h3,
                        color = MaterialTheme.LaskColors.textPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.LaskColors.brand_blue10
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.handleIntent(NewsFeedIntent.Refresh) },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                state.clusters.isEmpty() && state.error != null -> {
                    Text(
                        text = state.error!!,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.LaskColors.backgroundPrimary),
                    ) {
                        if (state.isOffline) {
                            item(key = "offline_banner") {
                                OfflineBanner(lastCachedAt = state.lastCachedAt)
                            }
                        }

                        state.clusters.forEach { cluster ->
                            stickyHeader(key = "header_${cluster.id}") {
                                ClusterHeader(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    title = cluster.leadArticle.title,
                                )
                            }

                            items(
                                items = cluster.articles,
                                key = { it.id },
                            ) { article ->
                                val isLast = article == cluster.articles.last()
                                NewsArticleCard(
                                    article = article,
                                    onClick = {
                                        viewModel.handleIntent(
                                            NewsFeedIntent.ArticleClick(
                                                articleId = article.id,
                                                articleUrl = article.url,
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                        .then(
                                            if (isLast) Modifier.padding(bottom = 24.dp)
                                            else Modifier
                                        ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}