package dev.alexmester.impl.presentation.components.body

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTypography
import dev.alexmester.ui.transition.sharedElementIfAvailable

@Composable
internal fun ArticleDetailTitle(
    title: String,
    articleId: Long,
) {
    Text(
        text = title,
        style = MaterialTheme.LaskTypography.h4,
        color = MaterialTheme.LaskColors.textPrimary,
        modifier = Modifier.sharedElementIfAvailable(key = "title_$articleId"),
    )
}