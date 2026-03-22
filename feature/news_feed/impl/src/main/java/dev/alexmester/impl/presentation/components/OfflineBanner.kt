package dev.alexmester.newsfeed.impl.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OfflineBanner(
    lastCachedAt: Long?,
    modifier: Modifier = Modifier,
) {
    val timeText = lastCachedAt?.let {
        val sdf = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())
        "Офлайн · обновлено ${sdf.format(Date(it))}"
    } ?: "Офлайн · нет данных"

    Text(
        text = timeText,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onErrorContainer,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(8.dp),
    )
}
