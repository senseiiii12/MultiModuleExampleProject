package dev.alexmester.impl.domain.model

data class SearchFilters(
    val category: String? = null,
    val country: String? = null,
    val language: String? = null,
    val earliestDate: String? = null,   // "yyyy-MM-dd"
    val latestDate: String? = null,     // "yyyy-MM-dd"
    val sortAscending: Boolean = false, // false = DESC (newest first)
) {
    val hasActiveFilters: Boolean
        get() = category != null || country != null || language != null
                || earliestDate != null || latestDate != null
}