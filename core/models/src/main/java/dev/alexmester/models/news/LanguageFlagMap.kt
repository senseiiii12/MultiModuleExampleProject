package dev.alexmester.models.news

/**
 * Маппинг кода языка → код страны для отображения emoji флага.
 * Для языков у которых код совпадает со страной (de→de, fr→fr)
 * запись не нужна — используем код языка напрямую как fallback.
 */
object LanguageFlagMap {

    val MAP: Map<String, String> = mapOf(
        "en" to "gb",
        "zh" to "cn",
        "ja" to "jp",
        "ko" to "kr",
        "ar" to "sa",
        "fa" to "ir",
        "uk" to "ua",
        "he" to "il",
        "vi" to "vn",
        "ms" to "my",
        "id" to "id",
        "th" to "th",
        "sv" to "se",
        "da" to "dk",
        "no" to "no",
        "fi" to "fi",
        "pl" to "pl",
        "cs" to "cz",
        "hu" to "hu",
        "ro" to "ro",
        "tr" to "tr",
        "pt" to "pt",
        "nl" to "nl",
        "de" to "de",
        "fr" to "fr",
        "it" to "it",
        "es" to "es",
        "ru" to "ru",
    )

    /**
     * Возвращает код страны для флага по коду языка.
     */
    fun flagCountryFor(languageCode: String): String =
        MAP[languageCode] ?: languageCode
}