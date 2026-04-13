package dev.alexmester.impl.presentation.locale_picker.components

import dev.alexmester.models.news.LanguageFlagMap
import dev.alexmester.models.news.SupportedLocales
import dev.alexmester.ui.components.common.countryToFlag
import java.util.Locale

object BuildLocaleItems {

    fun buildCountryItems(): List<LocaleItem> =
        SupportedLocales.SUPPORTED_COUNTRIES
            .map { code ->
                val locale = Locale("", code.uppercase())
                LocaleItem(
                    code = code,
                    displayName = locale.getDisplayCountry(Locale.ENGLISH)
                        .replaceFirstChar { it.uppercase() },
                    flag = countryToFlag(code),
                )
            }
            .sortedBy { it.displayName }

    fun buildLanguageItems(): List<LocaleItem> =
        SupportedLocales.SUPPORTED_LANGUAGES
            .map { code ->
                LocaleItem(
                    code = code,
                    displayName = Locale(code)
                        .getDisplayLanguage(Locale.ENGLISH)
                        .replaceFirstChar { it.uppercase() },
                    flag = countryToFlag(LanguageFlagMap.flagCountryFor(code)),
                )
            }
            .sortedBy { it.displayName }
}