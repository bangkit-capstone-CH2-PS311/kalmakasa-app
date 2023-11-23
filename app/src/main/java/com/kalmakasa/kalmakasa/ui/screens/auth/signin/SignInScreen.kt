package com.kalmakasa.kalmakasa.ui.screens.auth.signin

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.data.ResultState
import com.kalmakasa.kalmakasa.ui.component.EmailTextField
import com.kalmakasa.kalmakasa.ui.component.PasswordTextField
import com.kalmakasa.kalmakasa.ui.screens.auth.common.AuthButton
import com.kalmakasa.kalmakasa.ui.screens.auth.common.AuthTitle
import com.kalmakasa.kalmakasa.ui.state.EmailState
import com.kalmakasa.kalmakasa.ui.state.EmailStateSaver
import com.kalmakasa.kalmakasa.ui.state.PasswordState
import com.kalmakasa.kalmakasa.ui.state.PasswordStateSaver
import com.kalmakasa.kalmakasa.ui.theme.KalmakasaTheme

@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onGotoRegisterButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: SignInViewModel = hiltViewModel()

    val focusRequester = remember { FocusRequester() }
    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }
    val passwordState by rememberSaveable(stateSaver = PasswordStateSaver) {
        mutableStateOf(PasswordState())
    }
    val isValidated = emailState.isValid && passwordState.isValid

    // validate to form
    val onSubmit = {
        if (isValidated) {
            viewModel.login(emailState.text, passwordState.text)
        }
    }

    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()
    LaunchedEffect(loginState) {
        when (loginState) {
            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    (loginState as ResultState.Error).error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is ResultState.Success -> {
                Toast.makeText(
                    context,
                    (loginState as ResultState.Success).data,
                    Toast.LENGTH_SHORT
                ).show()
                onSignInSuccess()
            }

            else -> {}
        }
    }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = onGotoRegisterButtonClicked) {
                    Text(stringResource(R.string.create_an_account))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp)
        ) {
            AuthTitle(
                title = stringResource(R.string.sign_in),
                description = stringResource(R.string.welcome_back_sign_in_to_continue)
            )

            Spacer(modifier = Modifier.height(32.dp))

            EmailTextField(
                emailState = emailState,
                onImeAction = { focusRequester.requestFocus() },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                label = stringResource(R.string.password),
                passwordState = passwordState,
                onImeAction = onSubmit,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onForgotPasswordClicked) {
                    Text(text = stringResource(R.string.forgot_password))
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            AuthButton(
                authState = loginState,
                onClick = onSubmit,
                isEnabled = isValidated && loginState != ResultState.Loading,
                text = stringResource(R.string.sign_in)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun SignInPreview() {
    KalmakasaTheme {
        SignInScreen(
            onForgotPasswordClicked = {},
            onSignInSuccess = {},
            onGotoRegisterButtonClicked = {}
        )
    }
}