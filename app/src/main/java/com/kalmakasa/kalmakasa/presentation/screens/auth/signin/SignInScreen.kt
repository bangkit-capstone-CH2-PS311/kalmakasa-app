package com.kalmakasa.kalmakasa.presentation.screens.auth.signin

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
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Role
import com.kalmakasa.kalmakasa.presentation.screens.auth.common.AuthButton
import com.kalmakasa.kalmakasa.presentation.screens.auth.common.AuthTitle
import com.kalmakasa.kalmakasa.presentation.screens.auth.common.EmailTextField
import com.kalmakasa.kalmakasa.presentation.screens.auth.common.PasswordTextField
import com.kalmakasa.kalmakasa.presentation.screens.auth.register.AuthState
import com.kalmakasa.kalmakasa.presentation.state.EmailState
import com.kalmakasa.kalmakasa.presentation.state.EmailStateSaver
import com.kalmakasa.kalmakasa.presentation.state.PasswordState
import com.kalmakasa.kalmakasa.presentation.state.PasswordStateSaver
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun SignInScreen(
    loginState: AuthState,
    onSubmitted: (String, String) -> Unit,
    onSignInSuccess: (Role) -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onGotoRegisterButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusRequester = remember { FocusRequester() }
    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }
    val passwordState by rememberSaveable(stateSaver = PasswordStateSaver) {
        mutableStateOf(PasswordState())
    }

    // validate to form
    val isValidated = emailState.isValid && passwordState.isValid
    val onSubmit = {
        if (isValidated) {
            onSubmitted(emailState.text, passwordState.text)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(loginState) {
        if (loginState.isError) {
            Toast.makeText(
                context,
                loginState.message,
                Toast.LENGTH_SHORT
            ).show()
        }

        if (!loginState.isError && loginState.message.isNotBlank()) {
            Toast.makeText(
                context,
                loginState.message,
                Toast.LENGTH_SHORT
            ).show()
            loginState.role?.let {
                onSignInSuccess(it)
            }
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
                isEnabled = isValidated && !loginState.isLoading,
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
            loginState = AuthState(),
            onForgotPasswordClicked = {},
            onSignInSuccess = {},
            onSubmitted = { _, _ -> },
            onGotoRegisterButtonClicked = {}
        )
    }
}