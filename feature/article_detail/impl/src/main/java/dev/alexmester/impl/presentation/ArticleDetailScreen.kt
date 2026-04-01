package dev.alexmester.impl.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.alexmester.impl.presentation.components.ArticleDetailContent
import dev.alexmester.impl.presentation.components.ArticleDetailHeaderImage
import dev.alexmester.ui.R
import dev.alexmester.ui.components.snackbar.LaskTopSnackbarHost
import dev.alexmester.ui.components.snackbar.showLaskSnackbar
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskPalette
import dev.alexmester.ui.desing_system.LaskTypography
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.core.net.toUri

@Composable
fun ArticleDetailScreen(
    articleId: Long,
    articleUrl: String,
    onBack: () -> Unit,
    viewModel: ArticleDetailViewModel = koinViewModel(
        parameters = { parametersOf(articleId, articleUrl) }
    ),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                ArticleDetailSideEffect.NavigateBack -> onBack()
                is ArticleDetailSideEffect.ShowSnackbar ->
                    snackbarHostState.showLaskSnackbar(effect.message)
                is ArticleDetailSideEffect.ShareUrl -> {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, effect.url)
                    }
                    context.startActivity(Intent.createChooser(intent, "Поделиться"))
                }
                is ArticleDetailSideEffect.OpenBrowser -> {
                    context.startActivity(Intent(Intent.ACTION_VIEW, effect.url.toUri()))
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val s = state) {
            ArticleDetailScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.LaskColors.brand_blue,
                )
            }
            is ArticleDetailScreenState.Error -> {
                Text(
                    text = s.message,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.LaskTypography.body1,
                    color = MaterialTheme.LaskColors.error,
                )
            }
            is ArticleDetailScreenState.Content -> {
                ArticleDetailContent(
                    state = s,
                    onIntent = viewModel::handleIntent,
                )
            }
        }

        LaskTopSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
        )
    }
}