package com.kalmakasa.kalmakasa.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kalmakasa.kalmakasa.presentation.component.BottomBar
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.screens.article_detail.ArticleDetailScreen
import com.kalmakasa.kalmakasa.presentation.screens.article_detail.ArticleDetailViewModel
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ListArticleScreen
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ListArticleViewModel
import com.kalmakasa.kalmakasa.presentation.screens.auth.register.RegisterScreen
import com.kalmakasa.kalmakasa.presentation.screens.auth.register.RegisterViewModel
import com.kalmakasa.kalmakasa.presentation.screens.auth.signin.SignInScreen
import com.kalmakasa.kalmakasa.presentation.screens.auth.signin.SignInViewModel
import com.kalmakasa.kalmakasa.presentation.screens.auth.welcome.WelcomeScreen
import com.kalmakasa.kalmakasa.presentation.screens.consultant_detail.DetailDoctorScreen
import com.kalmakasa.kalmakasa.presentation.screens.consultant_detail.DetailDoctorViewModel
import com.kalmakasa.kalmakasa.presentation.screens.consultant_list.ListDoctorScreen
import com.kalmakasa.kalmakasa.presentation.screens.consultant_list.ListDoctorViewModel
import com.kalmakasa.kalmakasa.presentation.screens.home.HomeScreen
import com.kalmakasa.kalmakasa.presentation.screens.home.HomeViewModel
import com.kalmakasa.kalmakasa.presentation.screens.journal_add.AddJournalScreen
import com.kalmakasa.kalmakasa.presentation.screens.journal_add.AddJournalViewModel
import com.kalmakasa.kalmakasa.presentation.screens.launcher.LauncherViewModel
import com.kalmakasa.kalmakasa.presentation.screens.profile.ProfileScreen
import com.kalmakasa.kalmakasa.presentation.screens.question.QuestionScreen
import com.kalmakasa.kalmakasa.presentation.screens.question.QuestionViewModel
import com.kalmakasa.kalmakasa.presentation.state.SessionState


@Composable
fun KalmakasaApp() {
    val navController: NavHostController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (Screen.withBottomBar.contains(currentRoute)) {
                BottomBar(navController = navController)
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Launcher.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Launcher.route) {
                val viewModel: LauncherViewModel = hiltViewModel()
                val state by viewModel.sessionState.collectAsStateWithLifecycle()

                when (state) {
                    SessionState.Loading -> {
                        LoadingScreen()
                    }

                    SessionState.NotLoggedIn -> {
                        LaunchedEffect(state) {
                            navController.navigate(Screen.AuthGraph.route) {
                                popUpTo(Screen.Launcher.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }

                    is SessionState.LoggedIn -> {
                        LaunchedEffect(state) {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Launcher.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }

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
                arguments = listOf(navArgument("isNewUser") {
                    type = NavType.BoolType
                    defaultValue = false
                })
            ) {
                val viewModel: HomeViewModel = hiltViewModel()
                val homeState by viewModel.homeState.collectAsStateWithLifecycle()
                val articleState by viewModel.uiState.collectAsStateWithLifecycle()

                val isNewUser = it.arguments?.getBoolean("isNewUser", false) ?: false
                HomeScreen(
                    homeState = homeState,
                    articleState = articleState,
                    isNewUser = isNewUser,
                    onUserIsNotLoggedIn = {
                        navController.navigate(Screen.AuthGraph.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
//                    onLogoutClicked = {
//                        viewModel.logout()
//                        navController.navigate(Screen.AuthGraph.route) {
//                            popUpTo(Screen.Home.route) { inclusive = true }
//                        }
//                    },
                    navigateToListConsultant = {
                        navController.navigate(Screen.ListConsultant.route)
                    },
                    navigateToAssessment = { isSkippable ->
                        navController.navigate(Screen.Question.createRoute(isSkippable))
                    },
                    navigateToAddJournal = {
                        navController.navigate(Screen.AddJournal.route)
                    },
                    navigateToArticleList = {
                        navController.navigate(Screen.ListArticle.route)
                    },
                    onArticleClicked = { id ->
                        navController.navigate(Screen.DetailArticle.createRoute(id))
                    }
                )
            }

            composable(Screen.ListConsultant.route) {
                val viewModel: ListDoctorViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ListDoctorScreen(
                    uiState
                ) { doctor ->
                    navController.navigate(Screen.ConsultantDetail.createRoute(doctor.id))
                }
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            // FEATURES
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
                        navController.navigate(Screen.Home.route)
                    },
                    updateAnswer = viewModel::updateAnswer,
                    options = viewModel.options,
                    questions = viewModel.questions,
                    isSkippable = isSkippable,
                )
            }

            composable(Screen.History.route) {
                Text("History Screen")
            }

            composable(
                route = Screen.ConsultantDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val viewModel: DetailDoctorViewModel = hiltViewModel()
                val id = it.arguments?.getString("id") ?: ""
                LaunchedEffect(true) {
                    viewModel.getDoctorDetail(id)
                }

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                DetailDoctorScreen(
                    uiState = uiState,
                    navUp = { navController.navigateUp() },
                    onAppointmentBooked = {}
                )
            }

            composable(Screen.AddJournal.route) {
                val viewModel: AddJournalViewModel = hiltViewModel()

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                AddJournalScreen(
                    uiState = uiState,
                    navUp = {
                        navController.navigateUp()
                    },
                    nextStep = viewModel::nextStep,
                    prevStep = viewModel::prevStep,
                    onSliderChange = viewModel::onSliderChange,
                    onJournalChange = viewModel::onJournalChange,
                    onArticleClicked = { id ->
                        navController.navigate(Screen.DetailArticle.createRoute(id))
                    },
                    navigateToArticleList = {
                        navController.navigate(Screen.ListArticle.route)
                    },
                )
            }

            composable(Screen.ListArticle.route) {
                val viewModel: ListArticleViewModel = hiltViewModel()

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ListArticleScreen(
                    uiState = uiState,
                    onArticleClicked = { id ->
                        navController.navigate(Screen.DetailArticle.createRoute(id))
                    }
                )
            }

            composable(
                Screen.DetailArticle.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val viewModel: ArticleDetailViewModel = hiltViewModel()

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val id = it.arguments?.getString("id") ?: ""
                LaunchedEffect(true) {
                    viewModel.getArticleDetail(id)
                }

                if (uiState.isLoading) {
                    LoadingScreen()
                } else if (uiState.isError) {
                    Text("Error")
                } else {
                    uiState.article?.let { article ->
                        ArticleDetailScreen(
                            article = article,
                            navUp = { navController.navigateUp() }
                        )
                    } ?: LoadingScreen()
                }
            }
        }
    }

}


