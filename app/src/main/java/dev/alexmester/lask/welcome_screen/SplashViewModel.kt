package dev.alexmester.lask.welcome_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexmester.datastore.UserPreferencesDataSource
import dev.alexmester.datastore.util.DeviceLocaleProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SplashViewModel(
    private val preferencesDataSource: UserPreferencesDataSource,
) : ViewModel() {

    val state = preferencesDataSource.userPreferences
        .map { prefs ->
            SplashState.Ready(
                isOnboardingCompleted = prefs.isOnboardingCompleted,
                isLocaleManuallySet = prefs.isLocaleManuallySet,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = SplashState.Loading,
        )

    fun initLocaleIfNeeded(context: Context, isManuallySet: Boolean) {
        if (isManuallySet) return
        viewModelScope.launch {
            preferencesDataSource.initLocaleFromDevice(
                country = DeviceLocaleProvider.getCountry(context),
                language = DeviceLocaleProvider.getLanguage(context),
            )
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            preferencesDataSource.completeOnboarding()
        }
    }
}