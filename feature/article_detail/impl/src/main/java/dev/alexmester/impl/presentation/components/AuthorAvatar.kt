package dev.alexmester.impl.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTypography

@Composable
fun AuthorAvatar(
    char: String,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MaterialTheme.LaskColors.brand_blue10),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = char,
            style = MaterialTheme.LaskTypography.footnoteSemiBold,
            color = MaterialTheme.LaskColors.brand_blue,
        )
    }
}