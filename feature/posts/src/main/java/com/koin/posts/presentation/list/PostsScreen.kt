package com.koin.posts.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.koin.posts.domain.model.Post
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    onPostClick: (Int) -> Unit,
    viewModel: PostsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Posts") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is PostsUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is PostsUiState.Empty -> {
                    Text(
                        text = "No posts available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is PostsUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.posts) { post ->
                            PostItem(
                                post = post,
                                onClick = { onPostClick(post.id) }
                            )
                        }
                    }
                }
                is PostsUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: ${state.message}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.refreshPosts() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            if (isRefreshing) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "User ID: ${post.userId}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}