package dev.alexmester.impl.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexmester.ui.components.locale.LaskLocaleRowItem
import dev.alexmester.models.locale.LocaleItem
import dev.alexmester.ui.desing_system.LaskColors

val SEARCH_CATEGORIES = listOf(
    "politics", "sports", "business", "technology", "entertainment",
    "health", "science", "lifestyle", "travel", "culture", "education", "environment"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoryPickerScreen(
    selectedCategory: String?,
    onSelect: (String?) -> Unit,
    onBack: () -> Unit,
) {
    val items = SEARCH_CATEGORIES.map { cat ->
        LocaleItem(
            code = cat,
            displayName = cat.replaceFirstChar { it.uppercase() },
            flag = categoryEmoji(cat),
        )
    }

    Scaffold(
        topBar = {
            FilterPickerTopBar(
                title = "Category",
                isApplyEnabled = false,
                onBack = onBack,
                onApply = {},
                showApply = false,
            )
        },
        containerColor = MaterialTheme.LaskColors.backgroundPrimary,
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.LaskColors.backgroundPrimary)
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            // "All" option to clear
            item {
                LaskLocaleRowItem(
                    isSelected = selectedCategory == null,
                    item = LocaleItem(code = "", displayName = "All categories", flag = "🌐"),
                    onClick = { onSelect(null) },
                )
            }
            items(items, key = { it.code }) { item ->
                LaskLocaleRowItem(
                    isSelected = item.code == selectedCategory,
                    item = item,
                    onClick = { onSelect(item.code) },
                )
            }
        }
    }
}

private fun categoryEmoji(category: String): String = when (category) {
    "politics"      -> "🏛️"
    "sports"        -> "⚽"
    "business"      -> "💼"
    "technology"    -> "💻"
    "entertainment" -> "🎬"
    "health"        -> "🏥"
    "science"       -> "🔬"
    "lifestyle"     -> "🌿"
    "travel"        -> "✈️"
    "culture"       -> "🎨"
    "education"     -> "📚"
    "environment"   -> "🌍"
    else            -> "📰"
}