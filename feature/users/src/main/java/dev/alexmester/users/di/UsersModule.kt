package dev.alexmester.users.di

import dev.alexmester.users.data.remote.UserApiService
import dev.alexmester.users.data.repository.UserRepositoryImpl
import dev.alexmester.users.domain.repository.UserRepository
import dev.alexmester.users.domain.usecase.GetUsersUseCase
import dev.alexmester.users.domain.usecase.RefreshUsersUseCase
import dev.alexmester.users.presentation.list.UsersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val usersModule = module {

    single { UserApiService(httpClient = get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            userApiService = get(),
            userDao = get()
        )
    }

    factory { GetUsersUseCase(repository = get()) }
    factory { RefreshUsersUseCase(repository = get()) }

    viewModel {
        UsersViewModel(
            getUsersUseCase = get(),
            refreshUsersUseCase = get()
        )
    }
}