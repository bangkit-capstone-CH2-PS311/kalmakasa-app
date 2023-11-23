package com.kalmakasa.kalmakasa.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.kalmakasa.kalmakasa.SessionState
import com.kalmakasa.kalmakasa.ui.screens.auth.register.RegisterScreen
import com.kalmakasa.kalmakasa.ui.screens.auth.signin.SignInScreen
import com.kalmakasa.kalmakasa.ui.screens.auth.welcome.WelcomeScreen
import com.kalmakasa.kalmakasa.ui.screens.home.HomeScreen

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val SIGN_IN_ROUTE = "signin"
    const val REGISTER_ROUTE = "register"
    const val HOME_ROUTE = "home"
    const val SWITCHER_ROUTE = "switchers"

    const val APP_GRAPH = "app-graph"
    const val AUTH_GRAPH = "auth-graph"
}

@Composable
fun KalmakasaApp(uiState: SessionState) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.SWITCHER_ROUTE,
    ) {
        composable(Destinations.SWITCHER_ROUTE) {
            when (uiState) {
                SessionState.Loading -> {
                    LoadingScreen()
                }

                is SessionState.Success -> {
                    val destination =
                        if (uiState.session.isLogin) Destinations.APP_GRAPH else Destinations.AUTH_GRAPH
                    navController.navigate(destination) {
                        popUpTo(Destinations.SWITCHER_ROUTE) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }

        navigation(Destinations.WELCOME_ROUTE, Destinations.AUTH_GRAPH) {
            composable(Destinations.WELCOME_ROUTE) {
                WelcomeScreen(
                    onSignInClicked = {
                        navController.navigate(Destinations.SIGN_IN_ROUTE)
                    },
                    onRegisterClicked = {
                        navController.navigate(Destinations.REGISTER_ROUTE)
                    }
                )
            }
            composable(Destinations.SIGN_IN_ROUTE) {
                SignInScreen(
                    onGotoRegisterButtonClicked = {
                        navController.navigate(Destinations.REGISTER_ROUTE) {
                            popUpTo(Destinations.SIGN_IN_ROUTE) { inclusive = true }
                        }
                    },
                    onForgotPasswordClicked = {
                        navController.navigate(Destinations.APP_GRAPH)
                    },
                    onSignInSuccess = {
                        navController.navigate(Destinations.APP_GRAPH) {
                            popUpTo(Destinations.AUTH_GRAPH) { inclusive = true }
                        }
                    }
                )
            }
            composable(Destinations.REGISTER_ROUTE) {
                RegisterScreen(
                    onGotoSignInButtonClicked = {
                        navController.navigate(Destinations.SIGN_IN_ROUTE) {
                            popUpTo(Destinations.REGISTER_ROUTE) { inclusive = true }
                        }
                    },
                    onRegisterSuccess = {
                        navController.navigate(Destinations.APP_GRAPH) {
                            popUpTo(Destinations.AUTH_GRAPH) { inclusive = true }
                        }
                    }
                )
            }
        }
        navigation(Destinations.HOME_ROUTE, Destinations.APP_GRAPH) {
            composable(Destinations.HOME_ROUTE) {
                HomeScreen(
                    onLogoutClicked = {
                        navController.navigate(Destinations.AUTH_GRAPH) {
                            popUpTo(Destinations.APP_GRAPH) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}