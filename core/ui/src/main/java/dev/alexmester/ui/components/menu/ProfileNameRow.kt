package dev.alexmester.ui.components.menu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.alexmester.ui.R
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTheme
import dev.alexmester.ui.desing_system.LaskTypography

@Composable
fun ProfileNameRow(
    modifier: Modifier = Modifier,
    profileName: String,
    levelData: Levels,
    onEdit: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.LaskColors.backgroundPrimary),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = profileName,
                style = MaterialTheme.LaskTypography.h4,
                color = MaterialTheme.LaskColors.textPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = onEdit
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = ImageVector.vectorResource(R.drawable.ic_edit),
                    contentDescription = null,
                    tint = MaterialTheme.LaskColors.textSecondary
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(levelData.iconRes),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                text = levelData.title ,
                style = MaterialTheme.LaskTypography.body1,
                color = MaterialTheme.LaskColors.informative,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

enum class Levels(
    @param:DrawableRes val iconRes: Int,
    val title: String
) {
    LEVEL_1(R.drawable.ic_level_1, "Beginner"),
    LEVEL_2(R.drawable.ic_level_2, "Intermediate"),
    LEVEL_3(R.drawable.ic_level_3, "Advanced"),
    LEVEL_4(R.drawable.ic_level_4, "Pro"),
    LEVEL_5(R.drawable.ic_level_5, "Bookworm"),
}

@Preview(showBackground = true)
@Composable
private fun ProfileNameRowPreviewLight() {
    LaskTheme(darkTheme = false) {
        ProfileNameRow(
            modifier = Modifier,
            profileName = "Dianne Russell",
            levelData = Levels.LEVEL_5,
            onEdit = {}
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun ProfileNameRowPreviewDark() {
    LaskTheme(darkTheme = true) {
        ProfileNameRow(
            modifier = Modifier,
            profileName = "Dianne Russell",
            levelData = Levels.LEVEL_5,
            onEdit = {}
        )
    }
}