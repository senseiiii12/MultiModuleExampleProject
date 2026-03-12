package dev.alexmester.lask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.toArgb
import dev.alexmester.lask.ui.theme.LaskTheme
import dev.alexmester.lask.ui.theme.desing_system.LaskPalette

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = LaskPalette.BackgroundPrimaryLight.toArgb(),
                darkScrim  = LaskPalette.BackgroundPrimaryDark.toArgb(),
            )
        )

        setContent {
            LaskTheme {
                MainScreen()
            }
        }
    }
}

