package com.chepiga.database.di

import androidx.room.Room
import com.chepiga.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = "app_database"
        ).build()
    }

    single { get<AppDatabase>().postDao() }
    single { get<AppDatabase>().userDao() }
}