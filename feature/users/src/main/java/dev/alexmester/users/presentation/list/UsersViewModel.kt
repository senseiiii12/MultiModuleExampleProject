package dev.alexmester.users.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexmester.users.domain.model.User
import dev.alexmester.users.domain.usecase.GetUsersUseCase
import dev.alexmester.users.domain.usecase.RefreshUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val refreshUsersUseCase: RefreshUsersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiState: StateFlow<UsersUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadUsers()
        refreshUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .catch { e ->
                    _uiState.value = UsersUiState.Error(e.message ?: "Unknown error")
                }
                .collect { users ->
                    _uiState.value = if (users.isEmpty()) {
                        UsersUiState.Empty
                    } else {
                        UsersUiState.Success(users)
                    }
                }
        }
    }

    fun refreshUsers() {
        viewModelScope.launch {
            _isRefreshing.value = true
            refreshUsersUseCase()
                .onFailure { e ->
                    println("Refresh failed: ${e.message}")
                }
            _isRefreshing.value = false
        }
    }
}

sealed class UsersUiState {
    object Loading : UsersUiState()
    object Empty : UsersUiState()
    data class Success(val users: List<User>) : UsersUiState()
    data class Error(val message: String) : UsersUiState()
}