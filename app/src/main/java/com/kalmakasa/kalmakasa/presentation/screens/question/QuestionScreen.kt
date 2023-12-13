package com.kalmakasa.kalmakasa.presentation.screens.question

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun QuestionScreen(
    questionData: QuestionScreenData,
    questions: List<String>,
    @StringRes options: List<Int>,
    onNavUp: () -> Unit,
    onSubmit: () -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit,
    onSkipAssessment: () -> Unit,
    updateAnswer: (String) -> Unit,
    isSkippable: Boolean,
    modifier: Modifier = Modifier,
) {
    val question = questionData.currentQuestion
    val answer = questionData.currentAnswer
    val progress = questionData.progress


    val progressAnimation = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(),
        label = "progress"
    ).value

    Scaffold(
        topBar = {
            QuestionTopAppbar(
                title = "Questionnaire",
                onBackButtonClicked = if (isSkippable) onSkipAssessment
                else onNavUp,
                onSkipAssessment = onSkipAssessment,
                isSkippable = isSkippable,
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { onPreviousQuestion() },
                    modifier = Modifier.weight(1f),
                    enabled = questionData.currentQuestionIndex > 1,
                ) {
                    Text(text = stringResource(R.string.previous))
                }
                Button(
                    onClick = {
                        if (questionData.lastQuestion) onSubmit() else onNextQuestion()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = answer != null
                ) {
                    if (questionData.lastQuestion) {
                        Text(stringResource(R.string.done))
                    } else {
                        Text(stringResource(R.string.next))
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(
                    R.string.of_questions,
                    questionData.currentQuestionIndex,
                    questions.size
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall
            )
            LinearProgressIndicator(
                progress = progressAnimation,
                modifier = Modifier.fillMaxWidth()
            )
            QuestionBox(question)
            options.forEach { option ->
                OptionBox(
                    option = stringResource(option),
                    currentValue = answer,
                    onOptionClicked = updateAnswer
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopAppbar(
    title: String,
    onBackButtonClicked: () -> Unit,
    onSkipAssessment: () -> Unit,
    modifier: Modifier = Modifier,
    isSkippable: Boolean,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(title)
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (isSkippable) {
                TextButton(onClick = onSkipAssessment) {
                    Text(text = stringResource(R.string.skip))
                }
            }
        }
    )
}

@Composable
fun QuestionBox(
    question: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = question,
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.inverseOnSurface,
                MaterialTheme.shapes.small
            )
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun OptionBox(
    option: String,
    currentValue: String?,
    onOptionClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable {
                onOptionClicked(option)
            }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = option)
        Checkbox(checked = currentValue == option, onCheckedChange = null)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun QuestionPreview() {
    KalmakasaTheme {
        QuestionScreen(
            questionData = QuestionScreenData(),
            questions = listOf("a", "b", "c", "d"),
            options = emptyList(),
            onNavUp = {},
            onPreviousQuestion = { },
            onSubmit = {},
            onNextQuestion = {},
            updateAnswer = { _ -> },
            isSkippable = false,
            onSkipAssessment = {},
        )
    }
}