package com.kalmakasa.kalmakasa.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kalmakasa.kalmakasa.ui.screens.auth.register.RegisterScreen
import com.kalmakasa.kalmakasa.ui.screens.auth.register.RegisterViewModel
import com.kalmakasa.kalmakasa.ui.screens.auth.signin.SignInScreen
import com.kalmakasa.kalmakasa.ui.screens.auth.signin.SignInViewModel
import com.kalmakasa.kalmakasa.ui.screens.auth.welcome.WelcomeScreen
import com.kalmakasa.kalmakasa.ui.screens.home.HomeScreen
import com.kalmakasa.kalmakasa.ui.screens.home.HomeViewModel
import com.kalmakasa.kalmakasa.ui.screens.question.QuestionScreen
import com.kalmakasa.kalmakasa.ui.screens.question.QuestionViewModel

object Destinations {
    const val WELCOME_ROUTE = "welcome"
    const val SIGN_IN_ROUTE = "sign-in"
    const val REGISTER_ROUTE = "register"
    const val HOME_ROUTE = "home/{isNewUser}"
    const val QUESTION_ROUTE = "question/{isSkippable}"

    const val AUTH_GRAPH = "auth-graph"

    fun questionRoute(isSkippable: Boolean) = "question/$isSkippable"
    fun homeRoute(isNewUser: Boolean = true) = "home/$isNewUser"
}

@Composable
fun KalmakasaApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.HOME_ROUTE,
    ) {
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
                val viewModel: SignInViewModel = hiltViewModel()

                val loginState by viewModel.loginState.collectAsStateWithLifecycle()
                SignInScreen(
                    loginState = loginState,
                    onSubmitted = viewModel::login,
                    onGotoRegisterButtonClicked = {
                        navController.navigate(Destinations.REGISTER_ROUTE) {
                            popUpTo(Destinations.SIGN_IN_ROUTE) { inclusive = true }
                        }
                    },
                    onForgotPasswordClicked = {
                        navController.navigate(Destinations.homeRoute())
                    },
                    onSignInSuccess = {
                        navController.navigate(Destinations.homeRoute()) {
                            popUpTo(Destinations.AUTH_GRAPH) { inclusive = true }
                        }
                    }
                )
            }
            composable(Destinations.REGISTER_ROUTE) {
                val viewModel: RegisterViewModel = hiltViewModel()
                val registerState by viewModel.registerState.collectAsStateWithLifecycle()

                RegisterScreen(
                    registerState = registerState,
                    onGotoSignInButtonClicked = {
                        navController.navigate(Destinations.SIGN_IN_ROUTE) {
                            popUpTo(Destinations.REGISTER_ROUTE) { inclusive = true }
                        }
                    },
                    onSubmitted = viewModel::register,
                    onRegisterSuccess = {
                        navController.navigate(Destinations.homeRoute(true)) {
                            popUpTo(Destinations.AUTH_GRAPH) { inclusive = true }
                        }
                    }
                )
            }
        }

        // APP GRAPH
        composable(
            Destinations.HOME_ROUTE,
            arguments = listOf(navArgument("isNewUser") { type = NavType.BoolType })
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            val homeState by viewModel.homeState.collectAsStateWithLifecycle()

            val isNewUser = it.arguments?.getBoolean("isNewUser", false) ?: false
            HomeScreen(
                homeState,
                isNewUser = isNewUser,
                onUserIsNotLoggedIn = {
                    navController.navigate(Destinations.AUTH_GRAPH) {
                        popUpTo(Destinations.HOME_ROUTE) { inclusive = true }
                    }
                },
                onLogoutClicked = {
                    viewModel.logout()
                    navController.navigate(Destinations.AUTH_GRAPH) {
                        popUpTo(Destinations.HOME_ROUTE) { inclusive = true }
                    }
                },
                navigateToAssessment = { isSkippable ->
                    navController.navigate(Destinations.questionRoute(isSkippable))
                }
            )
        }

        composable(
            route = Destinations.QUESTION_ROUTE,
            arguments = listOf(navArgument("isSkippable") { type = NavType.BoolType })
        ) {
            val viewModel: QuestionViewModel = hiltViewModel()
            val questionScreenData by viewModel.uiState.collectAsStateWithLifecycle()

            val context = LocalContext.current
            val isSkippable = it.arguments?.getBoolean("isSkippable") ?: false
            QuestionScreen(
                questionData = questionScreenData,
                onPreviousQuestion = viewModel::previousQuestion,
                onNextQuestion = viewModel::nextQuestion,
                onSubmit = {
                    Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show()
                    navController.navigate(Destinations.homeRoute()) {
                        popUpTo(Destinations.QUESTION_ROUTE) { inclusive = true }
                    }
                },
                onNavUp = {
                    navController.navigateUp()
                },
                onSkipAssessment = {
                    navController.navigate(Destinations.homeRoute(false))
                },
                updateAnswer = viewModel::updateAnswer,
                options = viewModel.options,
                questions = viewModel.questions,
                isSkippable = isSkippable,
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}