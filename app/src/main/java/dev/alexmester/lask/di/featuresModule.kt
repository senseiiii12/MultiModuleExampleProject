package dev.alexmester.lask.di

import dev.alexmester.api.navigation.ArticleDetailApi
import dev.alexmester.api.navigation.NewsFeedApi
import dev.alexmester.impl.navigation.ArticleDetailImpl
import dev.alexmester.impl.navigation.NewsFeedImpl
import org.koin.dsl.module

val featuresModule = module {
    single<NewsFeedApi> { NewsFeedImpl(articleDetailApi = get()) }
    single<ArticleDetailApi> { ArticleDetailImpl() }
}