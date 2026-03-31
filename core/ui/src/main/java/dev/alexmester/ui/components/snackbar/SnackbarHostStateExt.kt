package dev.alexmester.ui.components.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

suspend fun SnackbarHostState.showLaskSnackbar(
    message: String,
    isError: Boolean = false,
    duration: SnackbarDuration = SnackbarDuration.Short,
) {
    showSnackbar(
        LaskSnackbarVisuals(
            message  = message,
            isError  = isError,
            duration = duration,
        )
    )
}