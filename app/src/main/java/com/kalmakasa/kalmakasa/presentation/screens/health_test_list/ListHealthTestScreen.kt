package com.kalmakasa.kalmakasa.presentation.screens.health_test_list

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.HealthTestLevel
import com.kalmakasa.kalmakasa.common.HealthTestType
import com.kalmakasa.kalmakasa.common.HealthTestType.Companion.getHealthTestLevel
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar

@Composable
fun ListHealthTestScreen(
    uiState: Resource<List<HealthTestResult>>,
    onItemClicked: (String) -> Unit,
    navigateToAssessment: () -> Unit,
    navUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = "Health Test History",
                onBackButtonClicked = navUp
            )
        },
        floatingActionButton = {
            Box(Modifier.padding(bottom = 16.dp)) {
                ExtendedFloatingActionButton(onClick = navigateToAssessment) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Take Test")
                }
            }
        }
    ) { paddingValues ->

        when (uiState) {
            is Resource.Loading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            is Resource.Error -> {
                ErrorScreen(Modifier.padding(paddingValues))
            }

            is Resource.Success -> {
                LazyColumn(
                    Modifier.padding(paddingValues)
                ) {
                    items(uiState.data, { it.id }) { item ->
                        HealthTestListItem(
                            id = item.id,
                            date = item.date,
                            stress = item.stress,
                            depression = item.depression,
                            anxiety = item.anxiety,
                            onItemClicked = onItemClicked
                        )
                        Divider()
                    }
                }
            }
        }


    }
}

@Composable
fun HealthTestListItem(
    id: String,
    date: String,
    stress: HealthTestType.Stress,
    depression: HealthTestType.Depression,
    anxiety: HealthTestType.Anxiety,
    onItemClicked: (String) -> Unit,
) {
    Column(
        Modifier
            .clickable { onItemClicked(id) }
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HealthTestChip(
                name = stringResource(R.string.stress),
                healthTestLevel = getHealthTestLevel(stress),
                modifier = Modifier.weight(1f)
            )
            HealthTestChip(
                name = stringResource(R.string.anxiety),
                healthTestLevel = getHealthTestLevel(anxiety),
                modifier = Modifier.weight(1f)

            )
            HealthTestChip(
                name = stringResource(R.string.depression),
                healthTestLevel = getHealthTestLevel(depression),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun HealthTestChip(
    name: String,
    healthTestLevel: HealthTestLevel,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = healthTestLevel.containerColor,
            contentColor = healthTestLevel.contentColor
        ),
        modifier = modifier
    ) {
        Text(
            text = name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}
