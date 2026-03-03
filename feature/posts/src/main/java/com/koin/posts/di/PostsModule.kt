package com.koin.posts.di

import com.koin.posts.data.remote.PostApiService
import com.koin.posts.data.repository.PostRepositoryImpl
import com.koin.posts.domain.repository.PostRepository
import com.koin.posts.domain.usecase.GetPostsUseCase
import com.koin.posts.domain.usecase.RefreshPostsUseCase
import com.koin.posts.presentation.list.PostsViewModel
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