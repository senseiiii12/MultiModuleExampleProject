package com.koin.network.di

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 30000
                socketTimeoutMillis = 30000
            }
            install(DefaultRequest) {
                url("https://jsonplaceholder.typicode.com/")
            }
        }
    }
}