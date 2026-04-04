package dev.alexmester.impl.presentation.components.body

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTypography

@Composable
internal fun ArticleDetailText(
    text: String?,
    summary: String?,
) {
    val displayText = text ?: summary ?: "Текст статьи недоступен"
    Text(
        text = displayText,
        style = MaterialTheme.LaskTypography.body1,
        color = MaterialTheme.LaskColors.textPrimary,
    )
}