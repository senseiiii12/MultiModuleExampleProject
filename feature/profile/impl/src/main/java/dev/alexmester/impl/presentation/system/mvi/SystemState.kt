package dev.alexmester.impl.presentation.system.mvi

data class SystemState(
    val theme: AppTheme = AppTheme.SYSTEM,
    val languageCode: String = "en",
    val countryCode: String = "us",
) {
    /** Полное название языка через системный Locale */
    val languageDisplayName: String
        get() = java.util.Locale(languageCode)
            .getDisplayLanguage(java.util.Locale.ENGLISH)
            .replaceFirstChar { it.uppercase() }

    /** Полное название страны через системный Locale */
    val countryDisplayName: String
        get() = java.util.Locale("", countryCode.uppercase())
            .getDisplayCountry(java.util.Locale.ENGLISH)
            .replaceFirstChar { it.uppercase() }
}