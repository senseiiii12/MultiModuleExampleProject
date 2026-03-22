package dev.alexmester.newsfeed.impl.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexmester.impl.domain.interactor.NewsFeedInteractor
import dev.alexmester.models.error.NetworkError
import dev.alexmester.models.result.AppResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val PAGE_SIZE = 20

class NewsFeedViewModel(
    private val interactor: NewsFeedInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(NewsFeedState())
    val state: StateFlow<NewsFeedState> = _state.asStateFlow()

    private val _sideEffects = Channel<NewsFeedSideEffect>(Channel.BUFFERED)
    val sideEffects = _sideEffects.receiveAsFlow()

    init {
        observeClusters()
        handleIntent(NewsFeedIntent.LoadFeed)
    }

    fun handleIntent(intent: NewsFeedIntent) {
        _state.update { NewsFeedReducer.reduce(it, intent) }

        when (intent) {
            is NewsFeedIntent.LoadFeed -> loadFeed()
            is NewsFeedIntent.Refresh -> refresh()
            is NewsFeedIntent.ArticleClick -> navigateToArticle(intent)
        }
    }

    private fun observeClusters() {
        interactor.getClustersFlow()
            .onEach { clusters ->
                val cachedAt = interactor.getLastCachedAt()
                _state.update {
                    NewsFeedReducer.onClustersLoaded(it, clusters, cachedAt)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadFeed() {
        viewModelScope.launch {
            when (val result = interactor.refresh(forceRefresh = false)) {
                is AppResult.Success -> _state.update { NewsFeedReducer.onRefreshSuccess(it) }
                is AppResult.Failure -> handleError(result.error)
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            when (val result = interactor.refresh(forceRefresh = true)) {
                is AppResult.Success -> _state.update { NewsFeedReducer.onRefreshSuccess(it) }
                is AppResult.Failure -> handleError(result.error)
            }
        }
    }

    private fun navigateToArticle(intent: NewsFeedIntent.ArticleClick) {
        viewModelScope.launch {
            _sideEffects.send(
                NewsFeedSideEffect.NavigateToArticle(
                    articleId = intent.articleId,
                    articleUrl = intent.articleUrl,
                )
            )
        }
    }

    private fun handleError(error: NetworkError) {
        when (error) {
            is NetworkError.NoInternet -> {
                _state.update { NewsFeedReducer.onOffline(it) }
            }
            is NetworkError.RateLimit -> {
                val message = "Превышен лимит запросов. Попробуйте позже"
                _state.update { NewsFeedReducer.onRefreshError(it, message) }
                viewModelScope.launch {
                    _sideEffects.send(NewsFeedSideEffect.ShowError(message))
                }
            }
            else -> {
                val message = "Ошибка загрузки новостей"
                _state.update { NewsFeedReducer.onRefreshError(it, message) }
                viewModelScope.launch {
                    _sideEffects.send(NewsFeedSideEffect.ShowError(message))
                }
            }
        }
    }
}
