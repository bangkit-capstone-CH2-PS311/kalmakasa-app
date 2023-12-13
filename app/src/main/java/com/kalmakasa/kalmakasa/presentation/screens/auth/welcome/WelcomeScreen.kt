package com.kalmakasa.kalmakasa.presentation.screens.auth.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun WelcomeScreen(
    onSignInClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        bottomBar = { WelcomeOptionButtons(onSignInClicked, onRegisterClicked) }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 32.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(R.drawable.welcome_image),
                    modifier = Modifier.width(320.dp),
                    contentDescription = null
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    stringResource(R.string.let_s_start_our_journey_towards_better_mental_wellbeing),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
fun WelcomeOptionButtons(
    onSignInClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onRegisterClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(stringResource(R.string.getting_started))
            }
            Spacer(Modifier.height(8.dp))
            TextButton(
                onClick = onSignInClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(stringResource(R.string.already_have_an_account))
            }
        }
    }
}

@Preview
@Composable
fun WelcomePreview() {
    KalmakasaTheme {
        WelcomeScreen(
            onSignInClicked = {},
            onRegisterClicked = {}
        )
    }
}