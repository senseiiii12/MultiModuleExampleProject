package com.koin.koinmudules

import android.app.Application
import com.chepiga.database.di.databaseModule
import com.koin.network.di.networkModule
import com.koin.posts.di.postsModule
import com.koin.users.di.usersModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)

            modules(
                // Core modules
                networkModule,
                databaseModule,

                // Feature modules
                postsModule,
                usersModule
            )
        }
    }
}