package dev.alexmester.impl.presentation.locale_picker.mvi

import dev.alexmester.api.navigation.LocalePickerType
import dev.alexmester.impl.presentation.locale_picker.components.LocaleItem

data class LocalePickerState(
    val type: LocalePickerType,
    val items: List<LocaleItem> = emptyList(),
    val selectedCode: String = "",
    val pendingCode: String = "",   // выбрано в UI, но ещё не применено
) {
    val title: String
        get() = when (type) {
            LocalePickerType.COUNTRY  -> "Country"
            LocalePickerType.LANGUAGE -> "Language"
        }

    /** Apply активен только если пользователь выбрал что-то отличное от текущего */
    val isApplyEnabled: Boolean
        get() = pendingCode.isNotEmpty() && pendingCode != selectedCode
}