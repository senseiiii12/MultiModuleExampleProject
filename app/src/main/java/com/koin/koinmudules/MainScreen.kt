package com.koin.koinmudules

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.koin.posts.presentation.list.PostsScreen
import com.koin.users.presentation.list.UsersScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = {},
                    label = { Text("Posts") },
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        navController.navigate("posts") {
                            popUpTo("posts") { inclusive = true }
                        }
                    }
                )
                NavigationBarItem(
                    icon = {},
                    label = { Text("Users") },
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        navController.navigate("users") {
                            popUpTo("users") { inclusive = true }
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "posts",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("posts") {
                PostsScreen(
                    onPostClick = { postId ->
                        // Навигация на детали поста (если понадобится)
                        println("Post clicked: $postId")
                    }
                )
            }
            composable("users") {
                UsersScreen()
            }
        }
    }
}