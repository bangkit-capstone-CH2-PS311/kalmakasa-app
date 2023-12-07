package com.kalmakasa.kalmakasa.presentation.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.domain.model.User
import com.kalmakasa.kalmakasa.presentation.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.screens.consultant_detail.TitleText
import com.kalmakasa.kalmakasa.presentation.state.SessionState
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun HomeScreen(
    homeState: SessionState,
    onLogoutClicked: () -> Unit,
    onUserIsNotLoggedIn: () -> Unit,
    navigateToListDoctor: () -> Unit,
    navigateToAssessment: (Boolean) -> Unit,
    navigateToAddJournal: () -> Unit,
    isNewUser: Boolean,
    modifier: Modifier = Modifier,
) {

    when (homeState) {
        SessionState.Loading -> {
            LoadingScreen()
        }

        SessionState.NotLoggedIn -> {
            LaunchedEffect(homeState) {
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
                    homeState.session,
                    onLogoutClicked = onLogoutClicked,
                    navigateToAssessment = { navigateToAssessment(false) },
                    navigateToListDoctor = navigateToListDoctor,
                    navigateToAddJournal = navigateToAddJournal,
                    modifier = modifier,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    user: User,
    onLogoutClicked: () -> Unit,
    navigateToAssessment: () -> Unit,
    navigateToListDoctor: () -> Unit,
    navigateToAddJournal: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 24.dp)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        val features = listOf(
            Feature(
                icon = Icons.Outlined.Favorite,
                title = "Consultation",
                onClick = navigateToListDoctor,
            ),
            Feature(
                icon = Icons.Outlined.MenuBook,
                title = "Journal",
                onClick = {},
            ),
            Feature(
                icon = Icons.Outlined.Article,
                title = "Article",
                onClick = {},
            )
        )
        val articles = listOf(
            Article(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                description = "Article 1",
                onClick = {},
            ),
            Article(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                description = "Article 2",
                onClick = {},
            ),
            Article(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                description = "Article 3",
                onClick = {},
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
            text = "How was your feeling?",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
        )
        Divider(Modifier.padding(vertical = 8.dp))
        Button(
            onClick = navigateToAddJournal,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .height(60.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
        ) {
            Text("Create your Daily Journal")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.ArrowForwardIos, contentDescription = null)
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
        Card(
            onClick = { navigateToAssessment() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ketahui Kesehatan Mentalmu",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Dapatkan gambaran kesehatan mentalmu dengan mengerjakan Tes Kesehatan",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                ElevatedButton(onClick = navigateToAssessment) {
                    Text("Start My Test")
                }
            }
        }

        // Article
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TitleText(text = "Article", Modifier)
            Text(
                "See More",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {

                    }
                    .padding(vertical = 8.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 0..articles.size step 2) {
                val first = articles[i]
                val sec = articles.getOrNull(i + 1)

                Row(Modifier.fillMaxWidth()) {
                    ArticleCard(
                        painter = first.painter,
                        description = first.description,
                        onClick = first.onClick,
                        Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                    if (sec != null) {
                        ArticleCard(
                            painter = sec.painter,
                            description = sec.description,
                            onClick = sec.onClick,
                            Modifier.weight(1f)
                        )
                    } else {
                        Spacer(
                            Modifier.weight(1f)
                        )
                    }
                }
                if (i != articles.lastIndex && i != (articles.lastIndex - 1)) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(
    painter: Painter,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .height(160.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(
                text = description,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis
            )
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
            homeState = SessionState.Loading,
            onLogoutClicked = {},
            onUserIsNotLoggedIn = {},
            navigateToListDoctor = {},
            navigateToAddJournal = {},
            navigateToAssessment = { _ -> },
            isNewUser = false
        )
    }
}

data class Feature(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)

data class Article(
    val painter: Painter,
    val description: String,
    val onClick: () -> Unit,
)