package dev.alexmester.datastore.util

import android.content.Context
import dev.alexmester.models.news.SupportedLocales

object DeviceLocaleProvider {

    fun getCountry(context: Context): String {
        val locale = context.resources.configuration.locales[0]
        val country = locale.country.lowercase()
        return if (country in SupportedLocales.SUPPORTED_COUNTRIES) country
        else SupportedLocales.FALLBACK_COUNTRY
    }

    fun getLanguage(context: Context): String {
        val locale = context.resources.configuration.locales[0]
        val language = locale.language.lowercase()
        return if (language in SupportedLocales.SUPPORTED_LANGUAGES) language
        else SupportedLocales.FALLBACK_LANGUAGE
    }
}