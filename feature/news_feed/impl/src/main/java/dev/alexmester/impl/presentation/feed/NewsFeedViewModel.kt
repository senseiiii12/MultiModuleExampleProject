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
        observeArticles()
        handleIntent(NewsFeedIntent.LoadFeed)
    }

    fun handleIntent(intent: NewsFeedIntent) {
        _state.update { NewsFeedReducer.reduce(it, intent) }

        when (intent) {
            is NewsFeedIntent.LoadFeed -> loadFeed()
            is NewsFeedIntent.Refresh -> refresh()
            is NewsFeedIntent.LoadMore -> loadMore()
            is NewsFeedIntent.ArticleClick -> navigateToArticle(intent)
            is NewsFeedIntent.SearchClick -> navigateToSearch()
        }
    }

    private fun observeArticles() {
        interactor.getArticlesFlow()
            .onEach { articles ->
                val cachedAt = interactor.getLastCachedAt()
                _state.update {
                    NewsFeedReducer.onArticlesLoaded(it, articles, cachedAt)
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

    private fun loadMore() {
        val currentState = _state.value
        if (currentState.isEndReached || currentState.isLoadingMore) return

        viewModelScope.launch {
            val nextOffset = currentState.currentOffset + PAGE_SIZE
            when (val result = interactor.loadMore(offset = nextOffset)) {
                is AppResult.Success -> _state.update {
                    NewsFeedReducer.onLoadMoreSuccess(
                        state = it,
                        newOffset = nextOffset,
                        isEndReached = false,
                    )
                }
                is AppResult.Failure -> {
                    _state.update { NewsFeedReducer.onLoadMoreError(it) }
                }
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

    private fun navigateToSearch() {
        viewModelScope.launch {
            _sideEffects.send(NewsFeedSideEffect.NavigateToSearch)
        }
    }

    private fun handleError(error: NetworkError) {
        when (error) {
            is NetworkError.NoInternet -> {
                _state.update { NewsFeedReducer.onOffline(it) }
            }
            is NetworkError.RateLimit -> {
                _state.update { NewsFeedReducer.onRefreshError(it, "Превышен лимит запросов") }
                viewModelScope.launch {
                    _sideEffects.send(NewsFeedSideEffect.ShowError("Превышен лимит запросов. Попробуйте позже"))
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
