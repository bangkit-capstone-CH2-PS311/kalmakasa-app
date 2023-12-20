package com.kalmakasa.kalmakasa.presentation.screens.launcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.kalmakasa.kalmakasa.common.Role
import com.kalmakasa.kalmakasa.presentation.Screen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.state.SessionState

@Composable
fun Launcher(
    state: SessionState,
    navController: NavHostController,
) {
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

                when (state.session.role) {
                    Role.Consultant.role -> {
                        navController.navigate(Screen.ConsultantGraph.route) {
                            popUpTo(Screen.Launcher.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    
                    else -> {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Launcher.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}