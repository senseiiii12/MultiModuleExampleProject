package dev.alexmester.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.alexmester.datastore.model.UserPreferences
import dev.alexmester.models.news.SupportedLocales
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesDataSource(
    private val dataStore: DataStore<Preferences>,
) {

    companion object {
        private val KEY_DEFAULT_COUNTRY = stringPreferencesKey("default_country")
        private val KEY_DEFAULT_LANGUAGE = stringPreferencesKey("default_language")
        private val KEY_IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        private val KEY_IS_THEME_SET = booleanPreferencesKey("is_theme_set")
        private val KEY_ONBOARDING_DONE = booleanPreferencesKey("onboarding_completed")
        private val KEY_LOCALE_MANUALLY_SET = booleanPreferencesKey("locale_manually_set")
    }

    val userPreferences: Flow<UserPreferences> = dataStore.data.map { prefs ->
        UserPreferences(
            defaultCountry = prefs[KEY_DEFAULT_COUNTRY] ?: SupportedLocales.FALLBACK_COUNTRY,
            defaultLanguage = prefs[KEY_DEFAULT_LANGUAGE] ?: SupportedLocales.FALLBACK_LANGUAGE,
            isDarkTheme = if (prefs[KEY_IS_THEME_SET] == true) prefs[KEY_IS_DARK_THEME] else null,
            isOnboardingCompleted = prefs[KEY_ONBOARDING_DONE] ?: false,
            isLocaleManuallySet = prefs[KEY_LOCALE_MANUALLY_SET] ?: false,
        )
    }

    suspend fun completeOnboarding() {
        dataStore.edit { it[KEY_ONBOARDING_DONE] = true }
    }

    suspend fun initLocaleFromDevice(country: String, language: String) {
        dataStore.edit { prefs ->
            prefs[KEY_DEFAULT_COUNTRY] = country
            prefs[KEY_DEFAULT_LANGUAGE] = language
            prefs[KEY_LOCALE_MANUALLY_SET] = false
        }
    }

    suspend fun updateLocaleManually(country: String, language: String) {
        dataStore.edit { prefs ->
            prefs[KEY_DEFAULT_COUNTRY] = country
            prefs[KEY_DEFAULT_LANGUAGE] = language
            prefs[KEY_LOCALE_MANUALLY_SET] = true
        }
    }

    /**
     * [isDark] null — сбросить на системную тему.
     */
    suspend fun updateTheme(isDark: Boolean?) {
        dataStore.edit { prefs ->
            if (isDark == null) {
                prefs.remove(KEY_IS_DARK_THEME)
                prefs[KEY_IS_THEME_SET] = false
            } else {
                prefs[KEY_IS_DARK_THEME] = isDark
                prefs[KEY_IS_THEME_SET] = true
            }
        }
    }
}