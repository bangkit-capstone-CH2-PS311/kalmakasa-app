package com.kalmakasa.kalmakasa.presentation.screens.consultant_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.SearchTopAppBar

@Composable
fun ListDoctorScreen(
    uiState: ListConsultantState,
    onConsultantClicked: (Consultant) -> Unit,
) {
    Scaffold(
        topBar = { SearchTopAppBar(query = uiState.searchQuery, placeholder = "Search...") }
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingScreen(Modifier.padding(paddingValues))
        } else if (uiState.isError) {
            Box(modifier = Modifier.padding(paddingValues)) {
                Text("Error")
            }
        } else {
            ListDoctorContent(
                listConsultant = uiState.listConsultant,
                onDoctorClicked = onConsultantClicked,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDoctorContent(
    listConsultant: List<Consultant>,
    onDoctorClicked: (Consultant) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    ) {
        items(listConsultant, {
            it.id
        }) { consultant ->
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                border = BorderStroke(1.dp, Color.Gray),
                shape = MaterialTheme.shapes.small,
                onClick = { onDoctorClicked(consultant) }
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp),
                ) {
                    Image(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.large)
                            .width(80.dp),
                        painter = painterResource(R.drawable.placeholder_consultant_img),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = consultant.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = consultant.speciality,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }
}