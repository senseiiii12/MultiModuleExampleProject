package dev.alexmester.impl.di

import dev.alexmester.impl.data.remote.SearchApiService
import dev.alexmester.impl.data.repository.SearchRepositoryImpl
import dev.alexmester.impl.domain.interactor.SearchInteractor
import dev.alexmester.impl.domain.repository.SearchRepository
import dev.alexmester.impl.presentation.mvi.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    single { SearchApiService(client = get()) }
    single<SearchRepository> {
        SearchRepositoryImpl(
            remote = get(),
            articleDao = get(),
        )
    }
    factory { SearchInteractor(repository = get()) }
    viewModel { SearchViewModel(interactor = get()) }
}