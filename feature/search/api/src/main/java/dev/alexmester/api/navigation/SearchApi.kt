package dev.alexmester.api.navigation

import dev.alexmester.navigation.FeatureApi

interface SearchApi : FeatureApi {
    fun searchRoute(): SearchRoute
}