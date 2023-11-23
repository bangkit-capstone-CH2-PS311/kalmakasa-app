package com.kalmakasa.kalmakasa.ui.screens.auth.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.data.ResultState

@Composable
fun AuthButton(
    authState: ResultState<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = isEnabled
    ) {
        if (authState == ResultState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp),
                strokeWidth = 2.5.dp
            )
        } else {
            Text(text)
        }
    }
}