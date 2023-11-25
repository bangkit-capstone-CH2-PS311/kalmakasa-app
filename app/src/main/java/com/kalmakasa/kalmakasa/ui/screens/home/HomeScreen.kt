package com.kalmakasa.kalmakasa.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kalmakasa.kalmakasa.ui.state.SessionState
import com.kalmakasa.kalmakasa.data.model.User
import com.kalmakasa.kalmakasa.ui.LoadingScreen
import com.kalmakasa.kalmakasa.ui.theme.KalmakasaTheme

@Composable
fun HomeScreen(
    homeState: SessionState,
    onLogoutClicked: () -> Unit,
    onUserIsNotLoggedIn: () -> Unit,
    navigateToAssessment: (Boolean) -> Unit,
    isNewUser: Boolean,
    modifier: Modifier = Modifier,
) {

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
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
                            navigateToAssessment = { navigateToAssessment(false) }
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun HomeContent(
    user: User,
    onLogoutClicked: () -> Unit,
    navigateToAssessment: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(user.name)
        Text(user.token)
        Text(user.isLogin.toString())
        Text(text = "Home Screen")
        Button(onClick = onLogoutClicked) {
            Text("Logout")
        }
        Button(onClick = {
            navigateToAssessment()
        }) {
            Text("Assessment")
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
            navigateToAssessment = { _ -> },
            isNewUser = false
        )
    }
}