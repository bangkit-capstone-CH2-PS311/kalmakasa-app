package com.kalmakasa.kalmakasa.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.domain.model.Journal
import com.kalmakasa.kalmakasa.domain.model.User
import com.kalmakasa.kalmakasa.presentation.component.LoadingContent
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ArticleCard
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ListArticleState
import com.kalmakasa.kalmakasa.presentation.screens.consultant_detail.TitleText
import com.kalmakasa.kalmakasa.presentation.screens.journal_list.JournalCard
import com.kalmakasa.kalmakasa.presentation.state.SessionState
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun HomeScreen(
    sessionState: SessionState,
    articleState: ListArticleState,
    onArticleClicked: (String) -> Unit,
    onUserIsNotLoggedIn: () -> Unit,
    navigateToConsultantList: () -> Unit,
    navigateToAssessment: (Boolean) -> Unit,
    navigateToAddJournal: () -> Unit,
    navigateToArticleList: () -> Unit,
    navigateToJournalList: () -> Unit,
    isNewUser: Boolean,
    modifier: Modifier = Modifier,
    journal: Journal? = null,
    navigateToChatBot: () -> Unit,
) {

    when (sessionState) {
        SessionState.Loading -> {
            LoadingScreen()
        }

        SessionState.NotLoggedIn -> {
            LaunchedEffect(sessionState) {
                onUserIsNotLoggedIn()
            }
        }

        is SessionState.LoggedIn -> {
            if (isNewUser) {
                LaunchedEffect(true) {
                    navigateToAssessment(true)
                }
            } else {
                HomeContent(
                    user = sessionState.session,
                    articleState = articleState,
                    onArticleClicked = onArticleClicked,
                    navigateToAssessment = { navigateToAssessment(false) },
                    navigateToConsultantList = navigateToConsultantList,
                    navigateToAddJournal = navigateToAddJournal,
                    navigateToArticleList = navigateToArticleList,
                    navigateToJournalList = navigateToJournalList,
                    navigateToChatBot = navigateToChatBot,
                    modifier = modifier,
                    journal = journal
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    user: User,
    articleState: ListArticleState,
    onArticleClicked: (String) -> Unit,
    navigateToAssessment: () -> Unit,
    navigateToConsultantList: () -> Unit,
    navigateToAddJournal: () -> Unit,
    navigateToArticleList: () -> Unit,
    navigateToJournalList: () -> Unit,
    navigateToChatBot: () -> Unit,
    modifier: Modifier = Modifier,
    journal: Journal? = null,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        val features = listOf(
            Feature(
                icon = Icons.Outlined.Favorite,
                title = stringResource(R.string.consultation),
                onClick = navigateToConsultantList,
            ),
            Feature(
                icon = Icons.Outlined.MenuBook,
                title = stringResource(R.string.journal),
                onClick = navigateToJournalList,
            ),
            Feature(
                icon = Icons.Outlined.Article,
                title = stringResource(R.string.article),
                onClick = navigateToArticleList,
            ),
            Feature(
                icon = Icons.Outlined.SmartToy,
                title = "Kalmbot",
                onClick = navigateToChatBot,
            )
        )

        // Title
        Text(
            text = stringResource(R.string.hi, user.name),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.how_was_your_feeling),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
        )
        Divider(Modifier.padding(vertical = 8.dp))

        if (journal != null) {
            JournalCard(mood = journal.mood, date = journal.date)
        } else {
            CreateJournalButton(
                navigateToAddJournal = navigateToAddJournal,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        // Features
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        ) {
            features.forEach { feature ->
                FeatureButton(
                    icon = feature.icon,
                    title = feature.title,
                    onClick = feature.onClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Assessment
        AssessmentCard(navigateToAssessment)

        // Article
        if (articleState.isLoading) {
            LoadingContent(
                modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
        } else if (articleState.isError) {
            Text("Error")
        } else {
            HomeArticles(
                articles = articleState.listArticle.take(4),
                navigateToArticleList = navigateToArticleList,
                onArticleClicked = onArticleClicked,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CreateJournalButton(
    navigateToAddJournal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = navigateToAddJournal,
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(stringResource(R.string.create_your_daily_journal))
        Spacer(modifier = Modifier.width(8.dp))
        Icon(Icons.Default.ArrowForwardIos, contentDescription = null)
    }
}

@Composable
fun HomeArticles(
    articles: List<Article>,
    title: String = stringResource(R.string.article),
    navigateToArticleList: (() -> Unit)? = null,
    onArticleClicked: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleText(text = title, Modifier)
        if (navigateToArticleList != null) {
            Text(
                stringResource(R.string.see_more),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { navigateToArticleList() }
                    .padding(vertical = 8.dp)
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in articles.indices step 2) {
            val first = articles[i]
            val sec = articles.getOrNull(i + 1)

            Row(Modifier.fillMaxWidth()) {
                ArticleCard(
                    imageUrl = first.imageUrl,
                    title = first.title,
                    description = first.description,
                    onClick = { onArticleClicked(first.id) },
                    modifier = Modifier.weight(1f),
                    tags = first.tags
                )
                Spacer(modifier = Modifier.width(18.dp))
                if (sec != null) {
                    ArticleCard(
                        imageUrl = sec.imageUrl,
                        title = sec.title,
                        description = sec.description,
                        onClick = { onArticleClicked(sec.id) },
                        modifier = Modifier.weight(1f),
                        tags = sec.tags
                    )
                } else {
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            if (i != articles.lastIndex && i != (articles.lastIndex - 1)) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentCard(
    navigateToAssessment: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = navigateToAssessment,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.know_your_mental_health),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.get_an_overview_of_your_mental_health_by_taking_the_health_test),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            ElevatedButton(onClick = navigateToAssessment) {
                Text(stringResource(R.string.start_my_test))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureButton(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Box(
                modifier = Modifier.padding(12.dp)
            ) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
        Text(title, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
fun HomePreview() {
    KalmakasaTheme {
        HomeScreen(
            sessionState = SessionState.Loading,
            articleState = ListArticleState(),
            onArticleClicked = { _ -> },
            onUserIsNotLoggedIn = {},
            navigateToConsultantList = {},
            navigateToAssessment = { _ -> },
            navigateToAddJournal = {},
            navigateToArticleList = {},
            navigateToJournalList = {},
            isNewUser = false,
        ) {

        }
    }
}

data class Feature(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)

