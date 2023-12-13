package com.kalmakasa.kalmakasa.presentation.screens.journal_add

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.MOODS
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar
import com.kalmakasa.kalmakasa.presentation.screens.home.HomeArticles
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme
import kotlin.math.roundToInt

@Composable
fun AddJournalScreen(
    uiState: AddJournalState,
    nextStep: () -> Unit,
    prevStep: () -> Unit,
    onSliderChange: (Float) -> Unit,
    onJournalChange: (String) -> Unit,
    onArticleClicked: (String) -> Unit,
    navigateToArticleList: () -> Unit,
    navUp: () -> Unit,
) {

    val nextEnable: Boolean = when (uiState.currentStep) {
        JournalStep.Journal -> uiState.journalValue.isNotEmpty()
        else -> true
    }

    val prevEnable: Boolean = when (uiState.currentStep) {
        JournalStep.Journal -> false
        JournalStep.Emotion -> true
        JournalStep.Recommendation -> false
    }

    Scaffold(
        topBar = {
            Column {
                TitleTopAppBar(
                    title = stringResource(R.string.new_journal),
                    onBackButtonClicked = navUp
                )
                LinearProgressIndicator(
                    progress = uiState.progress,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 24.dp)
                        .height(8.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxWidth(),
                )
            }
        },
        bottomBar = {
            if (uiState.currentStep != JournalStep.Recommendation) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = prevStep,
                        modifier = Modifier.weight(1f),
                        enabled = prevEnable,
                    ) {
                        Text(text = stringResource(R.string.previous))
                    }
                    Button(
                        onClick = nextStep,
                        modifier = Modifier.weight(1f),
                        enabled = nextEnable
                    ) {
                        Text(stringResource(R.string.next))
                    }
                }
            }
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingScreen(Modifier.padding(paddingValues))
        } else {
            when (uiState.currentStep) {
                JournalStep.Emotion -> MoodSlider(
                    sliderValue = uiState.sliderValue,
                    onSliderChange = onSliderChange,
                    modifier = Modifier.padding(paddingValues)
                )

                JournalStep.Journal -> MoodJournal(
                    value = uiState.journalValue,
                    onValueChange = onJournalChange,
                    modifier = Modifier.padding(paddingValues)
                )

                JournalStep.Recommendation -> {
                    JournalRecommendation(
                        uiState.recommendationContent,
                        navigateToArticleList,
                        onArticleClicked,
                        navUp,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }


    }
}

@Composable
fun JournalRecommendation(
    articles: List<Article>,
    navigateToArticleList: () -> Unit,
    onArticleClicked: (String) -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.thanks_for_telling_about_your_day),
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.here_some_recommendations_based_on_your_journal),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        HomeArticles(
            articles = articles,
            navigateToArticleList = navigateToArticleList,
            onArticleClicked = onArticleClicked,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = navigateToHome) {
            Text(stringResource(R.string.back_to_home))
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun MoodJournal(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.what_could_be_the_reason_behind_your_mood_today),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.hint_journal),
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                )
            }
        )
    }
}

@Composable
fun MoodSlider(
    sliderValue: Float,
    onSliderChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(verticalArrangement = Arrangement.Center) {

            val emoticonIndex = sliderValue.roundToInt()

            Image(
                painter = painterResource(MOODS[emoticonIndex].iconRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
            )

            Slider(
                value = sliderValue,
                onValueChange = onSliderChange,
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
        AddJournalScreen(
            AddJournalState(isLoading = false),
            {},
            {},
            { _ -> },
            { _ -> },
            {},
            {},
            {},
        )
    }
}