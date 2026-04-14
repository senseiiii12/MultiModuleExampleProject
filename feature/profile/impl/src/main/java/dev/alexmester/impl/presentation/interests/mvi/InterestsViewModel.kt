package dev.alexmester.impl.presentation.interests.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexmester.datastore.UserPreferencesDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class InterestsViewModel(
    private val preferencesDataSource: UserPreferencesDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(InterestsState())
    val state: StateFlow<InterestsState> = _state.asStateFlow()

    private val _sideEffects = Channel<InterestsSideEffect>(Channel.BUFFERED)
    val sideEffects = _sideEffects.receiveAsFlow()

    init {
        viewModelScope.launch {
            preferencesDataSource.userPreferences.collect { prefs ->
                _state.update { it.copy(interests = prefs.interests) }
            }
        }
    }

    fun handleIntent(intent: InterestsIntent) {
        when (intent) {
            is InterestsIntent.OnInputChange ->
                _state.update { it.copy(inputText = intent.text) }

            is InterestsIntent.Add -> addInterest()

            is InterestsIntent.Remove -> viewModelScope.launch {
                preferencesDataSource.removeInterest(intent.keyword)
            }

            is InterestsIntent.Back ->
                viewModelScope.launch { _sideEffects.send(InterestsSideEffect.NavigateBack) }
        }
    }

    private fun addInterest() {
        val keyword = _state.value.inputText.trim()
        if (keyword.isBlank()) return
        viewModelScope.launch {
            preferencesDataSource.addInterest(keyword)
            _state.update { it.copy(inputText = "") }
        }
    }
}