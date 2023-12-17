package com.kalmakasa.kalmakasa.presentation.screens.health_test_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.HealthTestType
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingContent
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar
import com.kalmakasa.kalmakasa.presentation.screens.home.HomeArticles

@Composable
fun DetailHealthTestScreen(
    uiState: Resource<HealthTestResult>,
    article: Resource<List<Article>>,
    onArticleClicked: (String) -> Unit,
    navUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.health_test_detail),
                onBackButtonClicked = navUp,
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is Resource.Loading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            is Resource.Error -> {
                ErrorScreen(Modifier.padding(paddingValues), uiState.error)
            }

            is Resource.Success -> {
                DetailHealthTestContent(
                    healthTestResult = uiState.data,
                    article = article,
                    onArticleClicked = onArticleClicked,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun DetailHealthTestContent(
    healthTestResult: HealthTestResult,
    article: Resource<List<Article>>,
    onArticleClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        OutlinedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = healthTestResult.date,
                    style = MaterialTheme.typography.titleMedium,
                )
                HealthScale(healthTestResult.depression)
                HealthScale(healthTestResult.anxiety)
                HealthScale(healthTestResult.stress)
                Text(
                    text = stringResource(R.string.health_test_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column {
            when (article) {
                is Resource.Loading -> {
                    LoadingContent(Modifier.height(120.dp))
                }

                is Resource.Success -> {
                    val listArticles = article.data.take(4)
                    HomeArticles(
                        title = stringResource(R.string.article_for_you),
                        articles = listArticles,
                        onArticleClicked = onArticleClicked,
                    )
                }

                else -> {}
            }

        }
    }
}

@Composable
fun HealthScale(
    healthTestType: HealthTestType,
) {
    val level = HealthTestType.getHealthTestScoring(healthTestType)
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(healthTestType.name),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(HealthTestType.getHealthTestScoring(healthTestType).desc),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = healthTestType.scale,
            color = level.containerColor,
            modifier = Modifier
                .height(12.dp)
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
        )
    }
}