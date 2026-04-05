package dev.alexmester.lask.di

import dev.alexmester.database.di.databaseModule
import dev.alexmester.datastore.di.dataStoreModule
import dev.alexmester.impl.di.articleDetailModule
import dev.alexmester.impl.di.newsFeedModule
import dev.alexmester.network.di.networkModule

object AppModules {
    val all = listOf(
        appScopeModule,
        splashModule,
        featuresModule,
        // Core
        networkModule,
        databaseModule,
        dataStoreModule,
        // Features
        newsFeedModule,
        articleDetailModule,
    )
}