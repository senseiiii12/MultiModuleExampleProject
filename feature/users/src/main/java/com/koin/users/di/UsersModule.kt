package com.koin.users.di

import com.koin.users.data.remote.UserApiService
import com.koin.users.data.repository.UserRepositoryImpl
import com.koin.users.domain.repository.UserRepository
import com.koin.users.domain.usecase.GetUsersUseCase
import com.koin.users.domain.usecase.RefreshUsersUseCase
import com.koin.users.presentation.list.UsersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val usersModule = module {

    // Data layer
    single { UserApiService(httpClient = get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            userApiService = get(),
            userDao = get()
        )
    }

    // Domain layer
    factory { GetUsersUseCase(repository = get()) }
    factory { RefreshUsersUseCase(repository = get()) }

    // Presentation layer
    viewModel { UsersViewModel(getUsersUseCase = get(), refreshUsersUseCase = get()) }
}