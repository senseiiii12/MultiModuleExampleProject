package dev.alexmester.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexmester.datastore.model.UserPreferences
import dev.alexmester.impl.domain.interactor.NewsFeedInteractor
import dev.alexmester.models.error.NetworkError
import dev.alexmester.models.result.AppResult
import dev.alexmester.newsfeed.impl.presentation.feed.ContentState
import dev.alexmester.newsfeed.impl.presentation.feed.NewsFeedIntent
import dev.alexmester.newsfeed.impl.presentation.feed.NewsFeedReducer
import dev.alexmester.newsfeed.impl.presentation.feed.NewsFeedScreenState
import dev.alexmester.newsfeed.impl.presentation.feed.NewsFeedSideEffect
import dev.alexmester.newsfeed.impl.presentation.feed.contentOrNull
import dev.alexmester.newsfeed.impl.presentation.feed.isContent
import dev.alexmester.newsfeed.impl.presentation.feed.isOffline
import dev.alexmester.ui.R
import dev.alexmester.ui.uitext.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsFeedViewModel(
    private val interactor: NewsFeedInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<NewsFeedScreenState>(NewsFeedScreenState.Loading)
    val state: StateFlow<NewsFeedScreenState> = _state.asStateFlow()

    private val _sideEffects = Channel<NewsFeedSideEffect>(Channel.BUFFERED)
    val sideEffects = _sideEffects.receiveAsFlow()

    init {
        observeClusters()
        observePreferencesChanges()
        loadFeed()
    }

    fun handleIntent(intent: NewsFeedIntent) {
        _state.update { NewsFeedReducer.reduce(it, intent) }

        when (intent) {
            is NewsFeedIntent.Refresh -> refresh()
            is NewsFeedIntent.ArticleClick -> navigateToArticle(intent)
        }
    }

    private fun observeClusters() {
        interactor.getClustersFlow().onEach { clusters ->
            if (clusters.isEmpty()) return@onEach
            val lastCachedAt = interactor.getLastCachedAt()
            val currentState = _state.value
            val country = interactor.getCountry()
            if (!currentState.isContent || !currentState.isOffline) {
                _state.update {
                    NewsFeedReducer.onClustersLoaded(clusters, lastCachedAt, country)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun observePreferencesChanges() {
        interactor.getPreferencesFlow()
            .drop(1)
            .distinctUntilChanged { old, new ->
                old.defaultCountry == new.defaultCountry &&
                old.defaultLanguage == new.defaultLanguage
            }
            .onEach { newPrefs ->
                _state.update { NewsFeedScreenState.Loading }
                loadFeed()
            }.launchIn(viewModelScope)
    }

    private fun loadFeed() {
        viewModelScope.launch {
            when (val result = interactor.refresh(forceRefresh = false)) {
                is AppResult.Success -> Unit
                is AppResult.Failure -> handleError(result.error)
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            when (val result = interactor.refresh(forceRefresh = true)) {
                is AppResult.Success -> Unit
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
        viewModelScope.launch {
            val message = error.toUiText()
            if (error is NetworkError.NoInternet) {
                val clusters = _state.value.contentOrNull?.clusters ?: emptyList()
                val lastCachedAt = interactor.getLastCachedAt()
                showError(message) { NewsFeedReducer.onOffline(clusters, lastCachedAt, message) }
            } else {
                showError(message)
            }
        }
    }

    private suspend fun showError(
        message: UiText,
        updateState: (NewsFeedScreenState) -> NewsFeedScreenState = {
            NewsFeedReducer.onError(it, message)
        }
    ) {
        val currentState = _state.value
        _state.update { updateState(currentState) }
        _sideEffects.send(NewsFeedSideEffect.ShowError(message))
    }

    private fun NetworkError.toUiText(): UiText = when (this) {
        is NetworkError.NoInternet -> UiText.StringResource(R.string.error_no_internet)
        is NetworkError.PaymentRequired -> UiText.StringResource(R.string.error_payment_required)
        is NetworkError.RateLimit -> UiText.StringResource(R.string.error_rate_limit)
        else -> UiText.StringResource(R.string.error_unknown)
    }
}
