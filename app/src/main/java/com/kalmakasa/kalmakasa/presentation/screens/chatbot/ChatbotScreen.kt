package com.kalmakasa.kalmakasa.presentation.screens.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Message
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar

@Composable
fun ChatbotScreen(
    messages: List<Message>,
    messageState: Resource<Boolean>,
    onSendMessage: (String) -> Unit,
    navUp: () -> Unit,
) {
    var textValue by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = "Kalmbot Chat Room",
                onBackButtonClicked = navUp,
            )
        },
        bottomBar = {
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 12.dp, horizontal = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = stringResource(R.string.type_a_message)) },
                    maxLines = 3,
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                )
                IconButton(
                    onClick = {
                        if (textValue.isNotBlank()) {
                            onSendMessage(textValue)
                            textValue = ""
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) {
                MessageBubble(it.msg, it.isUser)
            }
            if (messageState is Resource.Loading) {
                item {
                    MessageLoading()
                }
            }
        }
    }
}

@Composable
fun MessageLoading() {
    Row(Modifier.fillMaxWidth()) {
        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp
            ),
        ) {
            CircularProgressIndicator(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .size(32.dp),
                strokeWidth = 3.dp
            )
        }
    }
}

@Composable
fun MessageBubble(
    message: String,
    isUser: Boolean = true,
) {
    val arrangement = if (isUser) Arrangement.End else Arrangement.Start
    val shape = if (isUser) RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 16.dp,
    ) else RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomEnd = 16.dp
    )
    val color = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    )

    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = arrangement
    ) {
        Card(
            shape = shape,
            colors = if (isUser) color else CardDefaults.cardColors()
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}