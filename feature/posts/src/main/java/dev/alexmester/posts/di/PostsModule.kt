package dev.alexmester.posts.di

import dev.alexmester.posts.data.remote.PostApiService
import dev.alexmester.posts.data.repository.PostRepositoryImpl
import dev.alexmester.posts.domain.repository.PostRepository
import dev.alexmester.posts.domain.usecase.GetPostsUseCase
import dev.alexmester.posts.domain.usecase.RefreshPostsUseCase
import dev.alexmester.posts.presentation.list.PostsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val postsModule = module {

    // Data layer
    single { PostApiService(httpClient = get()) }

    single<PostRepository> {
        PostRepositoryImpl(
            postApiService = get(),
            postDao = get()
        )
    }

    // Domain layer
    factory { GetPostsUseCase(repository = get()) }
    factory { RefreshPostsUseCase(repository = get()) }

    // Presentation layer
    viewModel {
        PostsViewModel(
            getPostsUseCase = get(),
            refreshPostsUseCase = get()
        )
    }
}