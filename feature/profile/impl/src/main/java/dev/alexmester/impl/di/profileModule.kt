package dev.alexmester.impl.di

import dev.alexmester.api.navigation.ArticleListType
import dev.alexmester.api.navigation.LocalePickerType
import dev.alexmester.impl.data.local.ArticleListLocalDataSource
import dev.alexmester.impl.data.repository.ArticleListRepositoryImpl
import dev.alexmester.impl.domain.interactor.ArticleListInteractor
import dev.alexmester.impl.domain.interactor.ProfileInteractor
import dev.alexmester.impl.domain.repository.ArticleListRepository
import dev.alexmester.impl.presentation.article_list.mvi.ArticleListViewModel
import dev.alexmester.impl.presentation.interests.mvi.InterestsViewModel
import dev.alexmester.impl.presentation.locale_picker.mvi.LocalePickerViewModel
import dev.alexmester.impl.presentation.profile.mvi.ProfileViewModel
import dev.alexmester.impl.presentation.system.mvi.SystemViewModel
import dev.alexmester.models.di.DISPATCHER_IO
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileModule = module {

    single {
        ArticleListLocalDataSource(
            userStateDao = get(),
            ioDispatcher = get(named(DISPATCHER_IO)),
        )
    }

    single<ArticleListRepository> {
        ArticleListRepositoryImpl(local = get())
    }

    factory {
        ArticleListInteractor(repository = get())
    }
    factory {
        ProfileInteractor(
            preferencesDataSource = get(),
            articleUserStateDao = get(),
        )
    }

    viewModel { (type: ArticleListType) ->
        ArticleListViewModel(
            type = type,
            interactor = get(),
        )
    }
    viewModel {
        InterestsViewModel(preferencesDataSource = get())
    }
    viewModel {
        SystemViewModel(preferencesDataSource = get())
    }
    viewModel {
        ProfileViewModel(profileInteractor = get())
    }
    viewModel { (type: LocalePickerType) ->
        LocalePickerViewModel(
            type = type,
            preferencesDataSource = get(),
        )
    }
}