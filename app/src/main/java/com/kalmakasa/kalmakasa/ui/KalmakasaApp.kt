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

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SignIn : Screen("sign-in")
    object Register : Screen("register")
    object Home : Screen("home/{isNewUser}") {
        fun createRoute(isNewUser: Boolean = true) = "home/$isNewUser"
    }

    object Question : Screen("question/{isSkippable}") {
        fun createRoute(isSkippable: Boolean) = "question/$isSkippable"
    }

    object AuthGraph : Screen("auth-graph")
}


@Composable
fun KalmakasaApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        // AUTH GRAPH
        navigation(Screen.Welcome.route, Screen.AuthGraph.route) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onSignInClicked = {
                        navController.navigate(Screen.SignIn.route)
                    },
                    onRegisterClicked = {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            composable(Screen.SignIn.route) {
                val viewModel: SignInViewModel = hiltViewModel()

                val loginState by viewModel.loginState.collectAsStateWithLifecycle()
                SignInScreen(
                    loginState = loginState,
                    onSubmitted = viewModel::login,
                    onGotoRegisterButtonClicked = {
                        navController.navigate(Screen.Register.route) {
                            popUpTo(Screen.SignIn.route) { inclusive = true }
                        }
                    },
                    onForgotPasswordClicked = {
                        navController.navigate(Screen.Home.route)
                    },
                    onSignInSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.AuthGraph.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Register.route) {
                val viewModel: RegisterViewModel = hiltViewModel()
                val registerState by viewModel.registerState.collectAsStateWithLifecycle()

                RegisterScreen(
                    registerState = registerState,
                    onGotoSignInButtonClicked = {
                        navController.navigate(Screen.SignIn.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    },
                    onSubmitted = viewModel::register,
                    onRegisterSuccess = {
                        navController.navigate(Screen.Home.createRoute(true)) {
                            popUpTo(Screen.AuthGraph.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // APP GRAPH
        composable(
            Screen.Home.route,
            arguments = listOf(navArgument("isNewUser") { type = NavType.BoolType })
        ) {
            val viewModel: HomeViewModel = hiltViewModel()
            val homeState by viewModel.homeState.collectAsStateWithLifecycle()

            val isNewUser = it.arguments?.getBoolean("isNewUser", false) ?: false
            HomeScreen(
                homeState,
                isNewUser = isNewUser,
                onUserIsNotLoggedIn = {
                    navController.navigate(Screen.AuthGraph.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onLogoutClicked = {
                    viewModel.logout()
                    navController.navigate(Screen.AuthGraph.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                navigateToAssessment = { isSkippable ->
                    navController.navigate(Screen.Question.createRoute(isSkippable))
                }
            )
        }

        composable(
            route = Screen.Question.route,
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
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Question.route) { inclusive = true }
                    }
                },
                onNavUp = {
                    navController.navigateUp()
                },
                onSkipAssessment = {
                    navController.navigate(Screen.Home.createRoute(false))
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