package dev.alexmester.impl.presentation.locale_picker.mvi

sealed class LocalePickerIntent {
    data class SelectItem(val code: String) : LocalePickerIntent()
    data object Apply : LocalePickerIntent()
    data object Back : LocalePickerIntent()
}
