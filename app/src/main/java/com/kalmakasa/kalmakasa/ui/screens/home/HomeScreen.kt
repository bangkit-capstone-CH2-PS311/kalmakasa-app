package com.kalmakasa.kalmakasa.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kalmakasa.kalmakasa.data.model.PrefUser
import com.kalmakasa.kalmakasa.ui.theme.KalmakasaTheme

@Composable
fun HomeScreen(
    onLogoutClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val user by viewModel.user.collectAsStateWithLifecycle(PrefUser())

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(user.name)
                Text(user.token)
                Text(text = "Home Screen")
                Button(onClick = {
                    viewModel.logout()
                    onLogoutClicked()
                }) {
                    Text("Logout")
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    KalmakasaTheme {
        HomeScreen(onLogoutClicked = {})
    }
}