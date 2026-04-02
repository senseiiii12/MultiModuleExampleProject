package dev.alexmester.ui.components.error_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.alexmester.ui.desing_system.LaskColors
import dev.alexmester.ui.desing_system.LaskTypography

@Composable
fun LaskErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    isRetrying: Boolean,
    onRetry: ()-> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = isRetrying,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(tween(150)) + scaleIn(
                    tween(150),
                    initialScale = 0.8f,
                ) togetherWith fadeOut(tween(150)) + scaleOut(
                    tween(150),
                    targetScale = 0.8f,
                )
            },
            label = "errorContent",
        ) { isRefreshing ->
            if (isRefreshing) {
                CircularProgressIndicator(
                    color = MaterialTheme.LaskColors.brand_blue,
                    trackColor = MaterialTheme.LaskColors.brand_blue10,
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.LaskTypography.body1SemiBold,
                        color = MaterialTheme.LaskColors.error,
                    )
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.LaskColors.brand_blue10,
                        ),
                    ) {
                        Text(
                            text = "Retry",
                            style = MaterialTheme.LaskTypography.footnoteSemiBold,
                            color = MaterialTheme.LaskColors.textPrimary,
                        )
                    }
                }
            }
        }
    }
}