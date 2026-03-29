package dev.alexmester.lask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dev.alexmester.api.navigation.FeedRoute
import dev.alexmester.lask.welcome_screen.SplashState
import dev.alexmester.lask.welcome_screen.SplashViewModel
import dev.alexmester.lask.welcome_screen.WelcomeRoute
import dev.alexmester.ui.desing_system.LaskTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val splashViewModel: SplashViewModel by viewModel()

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.state.value is SplashState.Loading
        }

        setContent {
            val splashState by splashViewModel.state.collectAsState()
            LaunchedEffect(splashState) {
                val s = splashState
                if (s is SplashState.Ready) {
                    splashViewModel.initLocaleIfNeeded(
                        context = this@MainActivity,
                        isManuallySet = s.isLocaleManuallySet,
                    )
                }
            }
            LaskTheme {
                when (val s = splashState) {
                    SplashState.Loading -> Unit
                    is SplashState.Ready -> {
                        val navController = rememberNavController()
                        RootScreen(
                            navController = navController,
                            startDestination = if (s.isOnboardingCompleted) FeedRoute
                            else WelcomeRoute,
                            onOnboardingComplete = { splashViewModel.completeOnboarding() },
                        )
                    }
                }
            }
        }
    }
}

