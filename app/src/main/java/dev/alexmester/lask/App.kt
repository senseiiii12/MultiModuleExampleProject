package dev.alexmester.lask

import android.app.Application
import dev.alexmester.api.navigation.NewsFeedApi
import dev.alexmester.database.di.databaseModule
import dev.alexmester.datastore.di.dataStoreModule
import dev.alexmester.impl.di.newsFeedModule
import dev.alexmester.impl.navigation.NewsFeedImpl
import dev.alexmester.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module


val featuresModule = module {
    single<NewsFeedApi> { NewsFeedImpl() }
    // single<ExploreApi> { ExploreImpl() }
    // single<ArticleDetailApi> { ArticleDetailImpl() }
    // single<BookmarksApi> { BookmarksImpl() }
    // single<SettingsApi> { SettingsImpl() }
    // single<SearchApi> { SearchImpl() }
}

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)

            modules(
                // Core
                networkModule,
                databaseModule,
                dataStoreModule,

                // Feature DI
                newsFeedModule,

                // Feature Navigation
                featuresModule,
            )
        }
    }
}