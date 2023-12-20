package com.kalmakasa.kalmakasa.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.kalmakasa.kalmakasa.common.Role
import com.kalmakasa.kalmakasa.presentation.component.AppBottomBar
import com.kalmakasa.kalmakasa.presentation.component.ConsultantBottomBar
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_detail.DetailAppointmentScreen
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_detail.DetailAppointmentViewModel
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_list.ListAppointmentScreen
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_list.ListAppointmentViewModel
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_appointment_list.ListPatientAppointmentScreen
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_appointment_list.ListPatientAppointmentViewModel
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_list.ListPatientScreen
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_list.ListPatientViewModel
import com.kalmakasa.kalmakasa.presentation.screens.article_detail.ArticleDetailScreen
import com.kalmakasa.kalmakasa.presentation.screens.article_detail.ArticleDetailViewModel
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ListArticleScreen
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ListArticleViewModel
import com.kalmakasa.kalmakasa.presentation.screens.auth.register.RegisterScreen
import com.kalmakasa.kalmakasa.presentation.screens.auth.register.RegisterViewModel
import com.kalmakasa.kalmakasa.presentation.screens.auth.signin.SignInScreen
import com.kalmakasa.kalmakasa.presentation.screens.auth.signin.SignInViewModel
import com.kalmakasa.kalmakasa.presentation.screens.auth.welcome.WelcomeScreen
import com.kalmakasa.kalmakasa.presentation.screens.chatbot.ChatbotScreen
import com.kalmakasa.kalmakasa.presentation.screens.chatbot.ChatbotViewModel
import com.kalmakasa.kalmakasa.presentation.screens.consultant_detail.DetailConsultantScreen
import com.kalmakasa.kalmakasa.presentation.screens.consultant_detail.DetailDoctorViewModel
import com.kalmakasa.kalmakasa.presentation.screens.consultant_list.ListConsultantScreen
import com.kalmakasa.kalmakasa.presentation.screens.consultant_list.ListDoctorViewModel
import com.kalmakasa.kalmakasa.presentation.screens.health_test_detail.DetailHealthTestScreen
import com.kalmakasa.kalmakasa.presentation.screens.health_test_detail.DetailHealthTestViewModel
import com.kalmakasa.kalmakasa.presentation.screens.health_test_list.ListHealthTestScreen
import com.kalmakasa.kalmakasa.presentation.screens.health_test_list.ListHealthTestViewModel
import com.kalmakasa.kalmakasa.presentation.screens.home.HomeScreen
import com.kalmakasa.kalmakasa.presentation.screens.home.HomeViewModel
import com.kalmakasa.kalmakasa.presentation.screens.journal_add.AddJournalScreen
import com.kalmakasa.kalmakasa.presentation.screens.journal_add.AddJournalViewModel
import com.kalmakasa.kalmakasa.presentation.screens.journal_list.ListJournalScreen
import com.kalmakasa.kalmakasa.presentation.screens.journal_list.ListJournalViewModel
import com.kalmakasa.kalmakasa.presentation.screens.launcher.Launcher
import com.kalmakasa.kalmakasa.presentation.screens.launcher.LauncherViewModel
import com.kalmakasa.kalmakasa.presentation.screens.profile.ProfileScreen
import com.kalmakasa.kalmakasa.presentation.screens.profile.ProfileViewModel
import com.kalmakasa.kalmakasa.presentation.screens.question.QuestionScreen
import com.kalmakasa.kalmakasa.presentation.screens.question.QuestionViewModel
import com.kalmakasa.kalmakasa.presentation.screens.reservation_detail.DetailReservationScreen
import com.kalmakasa.kalmakasa.presentation.screens.reservation_detail.DetailReservationViewModel
import com.kalmakasa.kalmakasa.presentation.screens.reservation_list.ListReservationScreen
import com.kalmakasa.kalmakasa.presentation.screens.reservation_list.ListReservationViewModel


@Composable
fun KalmakasaApp() {
    val navController: NavHostController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (Screen.withBottomBar.contains(currentRoute)) {
                AppBottomBar(navController = navController)
            } else if (Screen.consultantBottomBar.contains(currentRoute)) {
                ConsultantBottomBar(navController = navController)
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

                Launcher(state = state, navController = navController)
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
                        onSignInSuccess = { role ->
                            if (role is Role.Consultant) {
                                navController.navigate(Screen.ConsultantGraph.route) {
                                    popUpTo(Screen.AuthGraph.route) { inclusive = true }
                                }
                            } else {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.AuthGraph.route) { inclusive = true }
                                }
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

            // CONSULTANT GRAPH
            navigation(Screen.ListAppointment.route, Screen.ConsultantGraph.route) {
                composable(Screen.ProfileConsultant.route) {
                    val viewModel: ProfileViewModel = hiltViewModel()
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    ProfileScreen(
                        uiState = uiState,
                        onLogoutClicked = {
                            viewModel.logout {
                                navController.navigate(Screen.AuthGraph.route) {
                                    popUpTo(Screen.ConsultantGraph.route) { inclusive = true }
                                }
                            }
                        },
                    )
                }

                composable(Screen.ListAppointment.route) {
                    val viewModel: ListAppointmentViewModel = hiltViewModel()

                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    ListAppointmentScreen(
                        uiState,
                        onAppointmentClicked = {
                            navController.navigate(Screen.DetailAppointment.createRoute(it))
                        }
                    )
                }

                composable(Screen.ListPatient.route) {
                    val viewModel: ListPatientViewModel = hiltViewModel()

                    val uiState by viewModel.patientState.collectAsStateWithLifecycle()
                    ListPatientScreen(
                        patientState = uiState,
                        navigateToAppointmentList = { id ->
                            navController.navigate(Screen.ListPatientAppointment.createRoute(id))
                        }
                    )
                }

                composable(
                    route = Screen.ListPatientAppointment.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) {
                    val viewModel: ListPatientAppointmentViewModel = hiltViewModel()

                    val id = it.arguments?.getString("id") ?: ""
                    LaunchedEffect(true) {
                        viewModel.getAppointment(id)
                    }

                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    ListPatientAppointmentScreen(
                        uiState,
                        onAppointmentClicked = { appointmentId ->
                            navController.navigate(
                                Screen.DetailAppointment.createRoute(appointmentId)
                            )
                        },
                        navUp = { navController.navigateUp() }
                    )
                }

                composable(
                    Screen.DetailAppointment.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) {
                    val viewModel: DetailAppointmentViewModel = hiltViewModel()

                    val id = it.arguments?.getString("id") ?: ""
                    LaunchedEffect(true) {
                        viewModel.getAppointmentDetail(id)
                    }

                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    DetailAppointmentScreen(
                        reservation = uiState.reservation,
                        reportState = uiState.reportState,
                        onAnswerChange = viewModel::updateAnswer,
                        uploadReport = { viewModel.uploadReport(id) },
                        prevStep = viewModel::previousQuestion,
                        nextStep = viewModel::nextQuestion,
                        navUp = { navController.navigateUp() }
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
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                val isNewUser = it.arguments?.getBoolean("isNewUser", false) ?: false
                HomeScreen(
                    sessionState = uiState.sessionState,
                    articleState = uiState.articleState,
                    journal = uiState.journal,
                    isNewUser = isNewUser,
                    onUserIsNotLoggedIn = {
                        navController.navigate(Screen.AuthGraph.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    navigateToConsultantList = {
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
                    navigateToChatBot = {
                        navController.navigate(Screen.Chatbot.route)
                    },
                    navigateToJournalList = {
                        navController.navigate(Screen.ListJournal.route)
                    },
                    onArticleClicked = { id ->
                        navController.navigate(Screen.DetailArticle.createRoute(id))
                    },
                )
            }

            // Profile Feature
            composable(Screen.Profile.route) {
                val viewModel: ProfileViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ProfileScreen(
                    uiState = uiState,
                    onLogoutClicked = {
                        viewModel.logout {
                            navController.navigate(Screen.AuthGraph.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    },
                    onHealthTestHistoryClicked = {
                        navController.navigate(Screen.ListHealthTestResult.route)
                    }
                )
            }

            // Chatbot Feature
            composable(Screen.Chatbot.route) {
                val viewModel: ChatbotViewModel = hiltViewModel()

                val uiState by viewModel.messageState.collectAsStateWithLifecycle()
                val messages by viewModel.messages.collectAsStateWithLifecycle()
                ChatbotScreen(
                    messages = messages,
                    messageState = uiState,
                    onSendMessage = viewModel::sendMessage,
                    navUp = { navController.navigateUp() }
                )
            }

            // Question Feature
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
                        viewModel.uploadAnswer(
                            onSuccessCallback = { id ->
                                navController.navigate(Screen.DetailHealthTestResult.createRoute(id)) {
                                    popUpTo(Screen.Question.route) { inclusive = true }
                                }
                            },
                            onErrorCallback = {
                                Toast.makeText(context, "Summit Failed", Toast.LENGTH_SHORT).show()
                            }
                        )
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

            // Health Test Features
            composable(Screen.ListHealthTestResult.route) {
                val viewModel: ListHealthTestViewModel = hiltViewModel()

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                ListHealthTestScreen(
                    uiState = uiState,
                    onItemClicked = {
                        navController.navigate(Screen.DetailHealthTestResult.createRoute(it))
                    },
                    navUp = {
                        navController.navigateUp()
                    },
                    navigateToAssessment = {
                        navController.navigate(Screen.Question.createRoute(false))
                    },
                )
            }

            composable(
                route = Screen.DetailHealthTestResult.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType }),
            ) {
                val viewModel: DetailHealthTestViewModel = hiltViewModel()
                val id = it.arguments?.getString("id") ?: ""
                LaunchedEffect(true) {
                    viewModel.getHealthTestDetail(id)
                }
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val articles by viewModel.article.collectAsStateWithLifecycle()
                DetailHealthTestScreen(
                    uiState = uiState,
                    navUp = { navController.navigateUp() },
                    article = articles,
                    onArticleClicked = { articleId ->
                        navController.navigate(Screen.DetailArticle.createRoute(articleId))
                    }
                )
            }

            // Consultant Features
            composable(Screen.ListConsultant.route) {
                val viewModel: ListDoctorViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ListConsultantScreen(
                    uiState = uiState,
                    onConsultantClicked = { consultant ->
                        navController.navigate(
                            Screen.ConsultantDetail.createRoute(consultant.profileId)
                        )
                    },
                    onQueryChange = viewModel::onQueryChange,
                    onFilterClicked = viewModel::onFilterClicked,
                    onForMeClicked = viewModel::onForMeClicked
                )
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
                DetailConsultantScreen(
                    uiState = uiState,
                    navUp = { navController.navigateUp() },
                    onAppointmentBooked = { checkout ->
                        viewModel.createReservation(checkout)
                    }
                )
            }

            // Journal Features
            composable(Screen.ListJournal.route) {
                val viewModel: ListJournalViewModel = hiltViewModel()

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ListJournalScreen(
                    uiState = uiState,
                    navUp = { navController.navigateUp() },
                    navigateToAddJournal = {
                        navController.navigate(Screen.AddJournal.route)
                    }
                )
            }
            composable(route = Screen.AddJournal.route) {
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

            // Reservation Feature
            composable(Screen.ListReservation.route) {
                val viewModel: ListReservationViewModel = hiltViewModel()

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                ListReservationScreen(
                    uiState = uiState,
                    onReservationClicked = {
                        navController.navigate(Screen.DetailReservation.createRoute(it))
                    }
                )
            }
            composable(
                Screen.DetailReservation.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val viewModel: DetailReservationViewModel = hiltViewModel()

                val id = it.arguments?.getString("id") ?: ""
                LaunchedEffect(true) {
                    viewModel.getReservationDetail(id)
                }
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                if (uiState.isLoading) {
                    LoadingScreen()
                } else if (uiState.isError) {
                    ErrorScreen(Modifier.padding(paddingValues))
                } else {
                    uiState.reservation?.let { reservation ->
                        DetailReservationScreen(
                            reservation = reservation,
                            navUp = {
                                navController.navigateUp()
                            }
                        )
                    } ?: ErrorScreen(Modifier.padding(paddingValues))

                }

            }

            // Article Features
            composable(Screen.ListArticle.route) {
                val viewModel: ListArticleViewModel = hiltViewModel()

                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                ListArticleScreen(
                    uiState = uiState,
                    onArticleClicked = { id ->
                        navController.navigate(Screen.DetailArticle.createRoute(id))
                    },
                    navUp = {
                        navController.navigateUp()
                    },
                    onQueryChange = viewModel::onQueryChange
                )
            }
            composable(
                Screen.DetailArticle.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                val viewModel: ArticleDetailViewModel = hiltViewModel()

                val id = it.arguments?.getString("id") ?: ""
                LaunchedEffect(true) {
                    viewModel.getArticleDetail(id)
                }
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()


                if (uiState.isLoading) {
                    LoadingScreen()
                } else if (uiState.isError) {
                    ErrorScreen(Modifier.padding(paddingValues))
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


