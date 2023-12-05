package com.kalmakasa.kalmakasa.presentation.screens.auth.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.presentation.state.EmailState
import com.kalmakasa.kalmakasa.presentation.state.PasswordState
import com.kalmakasa.kalmakasa.presentation.state.TextFieldState

@Composable
fun GeneralTextField(
    label: String,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    textFieldState: TextFieldState = remember { TextFieldState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
) {
    OutlinedTextField(
        value = textFieldState.text,
        onValueChange = {
            textFieldState.text = it
        },
        label = { Text(label) },
        modifier = modifier.onFocusChanged { focusState ->
            textFieldState.onFocusChange(focusState.isFocused)
            if (!textFieldState.isFocused) {
                textFieldState.enableShowErrors()
            }
        },
        leadingIcon = icon,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = textFieldState.showErrors(),
        supportingText = { textFieldState.getError()?.let { error -> Text(error) } },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        )
    )
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    emailState: TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
) {
    OutlinedTextField(
        value = emailState.text,
        onValueChange = {
            emailState.text = it
        },
        label = { Text(stringResource(R.string.email)) },
        modifier = modifier.onFocusChanged { focusState ->
            emailState.onFocusChange(focusState.isFocused)
            if (!emailState.isFocused) {
                emailState.enableShowErrors()
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = stringResource(R.string.email)
            )
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = emailState.showErrors(),
        supportingText = { emailState.getError()?.let { error -> Text(error) } },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        )
    )
}

@Composable
fun PasswordTextField(
    label: String,
    modifier: Modifier = Modifier,
    passwordState: TextFieldState = remember { PasswordState() },
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
) {
    val showPassword = rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = passwordState.text,
        onValueChange = {
            passwordState.text = it
            passwordState.enableShowErrors()
        },
        label = { Text(label) },
        modifier = modifier.onFocusChanged { focusState ->
            passwordState.onFocusChange(focusState.isFocused)
            if (!passwordState.isFocused) {
                passwordState.enableShowErrors()
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = passwordState.showErrors(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Key,
                contentDescription = stringResource(R.string.password)
            )
        },
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(R.string.hide_password),
                    )
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.show_password),
                    )
                }
            }
        },
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        supportingText = { passwordState.getError()?.let { error -> Text(error) } },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeAction() }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEmailTextField() {
    Column {
        EmailTextField()
        PasswordTextField("Password")
    }
}