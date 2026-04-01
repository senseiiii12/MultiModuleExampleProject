package dev.alexmester.impl.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskPalette
import dev.alexmester.ui.desing_system.LaskTypography

@Composable
fun SentimentRow(sentiment: Double) {
    val (label, color) = when {
        sentiment > 0.1 -> "Позитивная тональность" to LaskPalette.Sentiment_Positive
        sentiment < -0.1 -> "Негативная тональность" to LaskPalette.Sentiment_Negative
        else -> "Нейтральная тональность" to LaskPalette.Sentiment_Neutral
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = "●",
            color = color,
            style = MaterialTheme.LaskTypography.footnote,
        )
        Text(
            text = label,
            style = MaterialTheme.LaskTypography.footnote,
            color = MaterialTheme.LaskColors.textSecondary,
        )
    }
}