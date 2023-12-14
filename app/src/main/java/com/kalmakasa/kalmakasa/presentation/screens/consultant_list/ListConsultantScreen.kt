package com.kalmakasa.kalmakasa.presentation.screens.consultant_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.SearchTopAppBar

@Composable
fun ListConsultantScreen(
    uiState: ListConsultantState,
    onConsultantClicked: (Consultant) -> Unit,
    onQueryChange: (String) -> Unit,
    onFilterClicked: (String) -> Unit,
    onForMeClicked: () -> Unit,
) {
    var forMeState by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchTopAppBar(
                query = uiState.searchQuery,
                placeholder = stringResource(R.string.search),
                onQueryChange = onQueryChange,
                filters = uiState.filterValue,
                onFilterClicked = {
                    forMeState = false
                    onFilterClicked(it)
                },
                forMeState = forMeState,
                onForMeClicked = {
                    forMeState = !forMeState
                    onForMeClicked()
                },
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingScreen(Modifier.padding(paddingValues))
        } else if (uiState.isError) {
            ErrorScreen(Modifier.padding(paddingValues))
        } else {
            ListDoctorContent(
                listConsultant = uiState.listConsultant,
                onConsultantClicked = onConsultantClicked,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun ListDoctorContent(
    listConsultant: List<Consultant>,
    onConsultantClicked: (Consultant) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    ) {
        items(listConsultant, { it.id }) { consultant ->
            ConsultantCard(
                consultant = consultant,
                onConsultantClicked = onConsultantClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultantCard(
    consultant: Consultant,
    onConsultantClicked: (Consultant) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(1.dp, Color.Gray),
        shape = MaterialTheme.shapes.small,
        onClick = { onConsultantClicked(consultant) }
    ) {
        Row {
            AsyncImage(
                model = consultant.imageUrl,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .width(80.dp),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = consultant.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = consultant.speciality,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = consultant.expertise.joinToString(","),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}