package dev.alexmester.posts.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.alexmester.posts.domain.model.Post
import dev.alexmester.posts.domain.usecase.GetPostsUseCase
import dev.alexmester.posts.domain.usecase.RefreshPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class PostsViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val refreshPostsUseCase: RefreshPostsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PostsUiState>(PostsUiState.Loading)
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadPosts()
        refreshPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            getPostsUseCase()
                .catch { e ->
                    _uiState.value = PostsUiState.Error(e.message ?: "Unknown error")
                }
                .collect { posts ->
                    _uiState.value = if (posts.isEmpty()) {
                        PostsUiState.Empty
                    } else {
                        PostsUiState.Success(posts)
                    }
                }
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _isRefreshing.value = true
            refreshPostsUseCase()
                .onFailure { e ->
                    println("Refresh failed: ${e.message}")
                }
            _isRefreshing.value = false
        }
    }
}

sealed class PostsUiState {
    object Loading : PostsUiState()
    object Empty : PostsUiState()
    data class Success(val posts: List<Post>) : PostsUiState()
    data class Error(val message: String) : PostsUiState()
}