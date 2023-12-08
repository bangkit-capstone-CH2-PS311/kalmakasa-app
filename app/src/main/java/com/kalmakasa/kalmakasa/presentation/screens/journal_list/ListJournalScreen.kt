package com.kalmakasa.kalmakasa.presentation.screens.journal_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Mood
import com.kalmakasa.kalmakasa.domain.model.Journal
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun ListJournalScreen(
    uiState: ListJournalState,
    navUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.journal),
                onBackButtonClicked = navUp
            )
        },
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingScreen(Modifier.padding(paddingValues))
        } else if (uiState.isError) {
            Text(text = "Error")
        } else {
            ListJournalContent(
                journal = uiState.listJournal,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun ListJournalContent(
    journal: List<Journal>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier.padding(horizontal = 24.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(journal, { it.id }) { journal ->
            JournalCard(
                date = journal.date,
                mood = journal.mood,
                description = journal.description
            )
        }
    }
}

@Composable
fun JournalCard(
    mood: Mood,
    date: String,
    modifier: Modifier = Modifier,
    description: String? = null,
) {
    OutlinedCard(
        border = BorderStroke(1.dp, Color.LightGray),
        colors = CardDefaults.elevatedCardColors(Color.Transparent)
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painterResource(mood.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .border(
                            1.5.dp,
                            color = Color.Gray,
                            CircleShape
                        ),
                )
                Column {
                    Text(
                        date,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = stringResource(mood.stringRes),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ListJournalPreview() {
    KalmakasaTheme {
        ListJournalScreen(
            ListJournalState(),
            navUp = {}
        )
    }

}