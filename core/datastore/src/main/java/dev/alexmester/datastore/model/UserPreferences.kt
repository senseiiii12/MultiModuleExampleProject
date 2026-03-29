package dev.alexmester.datastore.model

import dev.alexmester.models.news.SupportedLocales

/**
 * Пользовательские настройки приложения.
 *
 * [defaultCountry] — код страны по умолчанию для ленты (например "us", "de", "fr").
 * Используется в feature:news-feed как параметр source-country для GET /top-news.
 *
 * [defaultLanguage] — код языка новостей (например "en", "de", "fr").
 *
 * [isDarkTheme] — тёмная или светлая тема.
 * null означает "следовать системной теме".
 */
data class UserPreferences(
    val defaultCountry: String = SupportedLocales.FALLBACK_COUNTRY,
    val defaultLanguage: String = SupportedLocales.FALLBACK_LANGUAGE,
    val isDarkTheme: Boolean? = null,
    val isOnboardingCompleted: Boolean = false,
    val isLocaleManuallySet: Boolean = false,
)