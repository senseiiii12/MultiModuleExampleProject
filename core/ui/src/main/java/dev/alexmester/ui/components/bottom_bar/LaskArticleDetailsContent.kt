package dev.alexmester.ui.components.bottom_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dev.alexmester.ui.R
import dev.alexmester.ui.desing_system.LaskColors

@Composable
fun LaskArticleDetailsContent(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardBackspace,
                    tint = MaterialTheme.LaskColors.textPrimary,
                    contentDescription = null
                )
            }
        }
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clap_filled),
                    tint = MaterialTheme.LaskColors.textPrimary,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkBorder,
                    tint = MaterialTheme.LaskColors.textPrimary,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_share),
                    tint = MaterialTheme.LaskColors.textPrimary,
                    contentDescription = null
                )
            }
        }
    }
}