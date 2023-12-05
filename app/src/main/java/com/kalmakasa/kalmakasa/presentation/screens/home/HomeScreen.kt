package com.kalmakasa.kalmakasa.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kalmakasa.kalmakasa.domain.model.User
import com.kalmakasa.kalmakasa.presentation.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.state.SessionState
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun HomeScreen(
    homeState: SessionState,
    onLogoutClicked: () -> Unit,
    onUserIsNotLoggedIn: () -> Unit,
    navigateToListDoctor: () -> Unit,
    navigateToAssessment: (Boolean) -> Unit,
    isNewUser: Boolean,
    modifier: Modifier = Modifier,
) {

    when (homeState) {
        SessionState.Loading -> {
            LoadingScreen()
        }

        SessionState.NotLoggedIn -> {
            LaunchedEffect(homeState) {
                onUserIsNotLoggedIn()
            }
        }

        is SessionState.LoggedIn -> {
            if (isNewUser) {
                LaunchedEffect(true) {
                    navigateToAssessment(true)
                }
            } else {
                HomeContent(
                    homeState.session,
                    onLogoutClicked = onLogoutClicked,
                    navigateToAssessment = { navigateToAssessment(false) },
                    navigateToListDoctor = navigateToListDoctor,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    user: User,
    onLogoutClicked: () -> Unit,
    navigateToAssessment: () -> Unit,
    navigateToListDoctor: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(user.name)
        Text(user.token)
        Text(user.isLogin.toString())
        Text(text = "Home Screen")
        Button(onClick = onLogoutClicked) {
            Text("Logout")
        }
        Button(onClick = { navigateToAssessment() }) {
            Text("Assessment")
        }
        Button(onClick = navigateToListDoctor) {
            Text("Doctor List")
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    KalmakasaTheme {
        HomeScreen(
            homeState = SessionState.Loading,
            onLogoutClicked = {},
            onUserIsNotLoggedIn = {},
            navigateToListDoctor = {},
            navigateToAssessment = { _ -> },
            isNewUser = false
        )
    }
}