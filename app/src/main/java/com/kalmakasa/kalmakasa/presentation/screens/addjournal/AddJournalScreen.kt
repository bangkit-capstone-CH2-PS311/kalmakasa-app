package com.kalmakasa.kalmakasa.presentation.screens.addjournal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalScreen() {
    var step by rememberSaveable { mutableIntStateOf(1) }
    var value by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Create Journal") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
                LinearProgressIndicator(
                    progress = step / 2f,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 24.dp)
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxWidth(),
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { step++ },
                    modifier = Modifier.weight(1f),
                    enabled = true,
                ) {
                    Text(text = stringResource(R.string.previous))
                }
                Button(
                    onClick = { step-- },
                    modifier = Modifier.weight(1f),
                    enabled = true
                ) {
                    Text(stringResource(R.string.next))
                }
            }
        }
    ) { paddingValues ->
        when (step) {
            1 -> MoodSlider(Modifier.padding(paddingValues))
            else -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(vertical = 16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "What could be the reason behind your mood today?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    )

                    OutlinedTextField(
                        value = value,
                        onValueChange = { value = it },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxSize(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = {
                            Text(
                                text = "The main reason is...",
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun MoodSlider(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(verticalArrangement = Arrangement.Center) {

            var sliderValue by rememberSaveable { mutableFloatStateOf(2f) }
            val emoticon = listOf(
                "Very Sad",
                "Sad",
                "Flat",
                "Happy",
                "Very Happy",
            )
            val emoticonIndex = sliderValue.roundToInt()

            Text(
                text = emoticon[emoticonIndex],
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                textAlign = TextAlign.Center,
            )

            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                valueRange = 0f..4f,
                steps = 3
            )

        }
    }
}

@Preview
@Composable
fun AddJournalPreview() {
    KalmakasaTheme {
        AddJournalScreen()
    }
}