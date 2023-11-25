package com.kalmakasa.kalmakasa.ui.screens.auth.register

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
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
import com.kalmakasa.kalmakasa.data.ResultState
import com.kalmakasa.kalmakasa.ui.component.EmailTextField
import com.kalmakasa.kalmakasa.ui.component.GeneralTextField
import com.kalmakasa.kalmakasa.ui.component.PasswordTextField
import com.kalmakasa.kalmakasa.ui.screens.auth.common.AuthButton
import com.kalmakasa.kalmakasa.ui.screens.auth.common.AuthTitle
import com.kalmakasa.kalmakasa.ui.state.EmailState
import com.kalmakasa.kalmakasa.ui.state.EmailStateSaver
import com.kalmakasa.kalmakasa.ui.state.PasswordState
import com.kalmakasa.kalmakasa.ui.state.PasswordStateSaver
import com.kalmakasa.kalmakasa.ui.state.TextFieldState
import com.kalmakasa.kalmakasa.ui.theme.KalmakasaTheme

@Composable
fun RegisterScreen(
    registerState: ResultState<String>,
    onGotoSignInButtonClicked: () -> Unit,
    onSubmitted: (String, String, String) -> Unit,
    onRegisterSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val nameState by remember {
        mutableStateOf(
            TextFieldState(
                validator = { name -> name.isNotEmpty() },
                errorFor = { _ -> "This field is required" }
            )
        )
    }
    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }
    val passwordState by rememberSaveable(stateSaver = PasswordStateSaver) {
        mutableStateOf(PasswordState())
    }


    val isValidated = nameState.isValid && emailState.isValid && passwordState.isValid
    val onSubmit = {
        if (isValidated) {
            onSubmitted(nameState.text, emailState.text, passwordState.text)
        }
    }

    val context = LocalContext.current
    LaunchedEffect(registerState) {
        when (registerState) {
            is ResultState.Error -> {
                Toast.makeText(
                    context,
                    registerState.error,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is ResultState.Success -> {
                Toast.makeText(
                    context,
                    registerState.data,
                    Toast.LENGTH_SHORT
                ).show()
                onRegisterSuccess()
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
                TextButton(onClick = onGotoSignInButtonClicked) {
                    Text(stringResource(R.string.already_have_an_account))
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
                title = stringResource(R.string.register),
                description = stringResource(R.string.ready_to_get_started_create_your_account_to_continue_the_journey),
            )

            Spacer(modifier = Modifier.height(32.dp))

            GeneralTextField(
                label = stringResource(R.string.full_name),
                modifier = Modifier.fillMaxWidth(),
                textFieldState = nameState,
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Person, contentDescription = stringResource(
                            R.string.full_name
                        )
                    )
                },
                onImeAction = {
                    focusRequester.requestFocus()
                })

            Spacer(modifier = Modifier.height(8.dp))

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

            Spacer(modifier = Modifier.height(40.dp))

            AuthButton(
                authState = registerState,
                onClick = onSubmit,
                isEnabled = isValidated && registerState != ResultState.Loading,
                text = stringResource(R.string.register)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun RegisterPreview() {
    KalmakasaTheme {
        RegisterScreen(
            registerState = ResultState.Success("Successful"),
            onRegisterSuccess = { },
            onSubmitted = { _, _, _ -> },
            onGotoSignInButtonClicked = { }
        )
    }
}