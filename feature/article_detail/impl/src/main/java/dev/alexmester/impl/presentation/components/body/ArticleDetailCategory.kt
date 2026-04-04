package dev.alexmester.impl.presentation.components.body

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTypography

@Composable
internal fun ArticleDetailCategory(category: String?) {
    category ?: return
    Text(
        text = category.replaceFirstChar { it.uppercase() },
        style = MaterialTheme.LaskTypography.footnote,
        color = MaterialTheme.LaskColors.brand_blue,
        modifier = Modifier.padding(bottom = 8.dp),
    )
}