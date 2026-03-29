package dev.alexmester.lask.welcome_screen

sealed interface SplashState {
    data object Loading : SplashState
    data class Ready(
        val isOnboardingCompleted: Boolean,
        val isLocaleManuallySet: Boolean
    ) : SplashState
}