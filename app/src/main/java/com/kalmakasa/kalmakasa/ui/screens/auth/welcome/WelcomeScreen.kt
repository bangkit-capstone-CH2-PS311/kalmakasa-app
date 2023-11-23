package com.kalmakasa.kalmakasa.ui.screens.auth.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.ui.theme.KalmakasaTheme

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
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome Screen",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center
            )
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
            .padding(vertical = 8.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onSignInClicked,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.sign_in))
            }
            OutlinedButton(
                onClick = onRegisterClicked,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.register))
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