package com.kalmakasa.kalmakasa.presentation.screens.article_list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Tag
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.SearchTopAppBar

@Composable
fun ListArticleScreen(
    uiState: ListArticleState,
    onQueryChange: (String) -> Unit,
    onArticleClicked: (String) -> Unit,
    navUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SearchTopAppBar(
                query = uiState.query,
                onQueryChange = onQueryChange,
                placeholder = stringResource(R.string.search_article),
                navUp = navUp,
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingScreen(Modifier.padding(paddingValues))
        } else if (uiState.isError) {
            ErrorScreen(Modifier.padding(paddingValues))
        } else {
            ListArticleContent(
                articles = uiState.listArticle,
                modifier = Modifier.padding(paddingValues),
                onArticleClicked = onArticleClicked,
            )
        }
    }
}

@Composable
fun ListArticleContent(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    onArticleClicked: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(articles, { it.id }) { article ->
            ArticleCard(
                imageUrl = article.imageUrl,
                title = article.title,
                description = article.description,
                tags = article.tags,
                onClick = { onArticleClicked(article.id) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCard(
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tags: List<Tag>,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .height(180.dp)
    ) {
        AsyncImage(
            model = imageUrl,
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState()),
            ) {
                tags.forEach {
                    OutlinedCard(shape = MaterialTheme.shapes.small) {
                        Text(
                            text = it.text,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun ListArticlePreview() {
    ListArticleScreen(
        uiState = ListArticleState(),
        onArticleClicked = {},
        navUp = {},
        onQueryChange = {}
    )
}