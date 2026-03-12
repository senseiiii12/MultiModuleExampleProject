package dev.alexmester.lask.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import dev.alexmester.lask.ui.theme.desing_system.LaskDarkColors
import dev.alexmester.lask.ui.theme.desing_system.LaskLightColors
import dev.alexmester.lask.ui.theme.desing_system.LocalLaskColors


@Composable
fun LaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: LaskTypography = LaskTypography(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) LaskDarkColors else LaskLightColors
    CompositionLocalProvider(
        LocalLaskColors provides colors,
        LocalLaskTypography provides typography,
    ) {
        MaterialTheme(
            content = content
        )
    }
}