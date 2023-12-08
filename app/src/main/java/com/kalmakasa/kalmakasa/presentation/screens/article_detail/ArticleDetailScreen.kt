package com.kalmakasa.kalmakasa.presentation.screens.article_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.data.network.FakeArticleDataSource
import com.kalmakasa.kalmakasa.domain.model.Article

@Composable
fun ArticleDetailScreen(
    article: Article,
    navUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = stringResource(R.string.article_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Gray, Color.Transparent
                            )
                        )
                    ),
            ) {
                IconButton(onClick = navUp) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                article.description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun ArticleDetailPreview() {
    MaterialTheme {
        ArticleDetailScreen(article = FakeArticleDataSource.articles[0], {})
    }
}