package com.kalmakasa.kalmakasa.presentation.screens.consultant_list

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.presentation.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.VerticalDivider
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDoctorScreen(
    uiState: ListConsultantState,
    onConsultantClicked: (Consultant) -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 16.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    shape = MaterialTheme.shapes.extraLarge,
                    onValueChange = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    placeholder = { Text("Search...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .height(FilterChipDefaults.Height)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(Modifier.width(16.dp))
                    FilterChip(
                        selected = false,
                        onClick = {},
                        label = { Text("For Me") },
                        leadingIcon = { Icon(Icons.Outlined.AutoAwesome, null) },
                    )
                    VerticalDivider()
                    FilterChip(selected = false, onClick = {}, label = { Text("Filter 1") })
                    FilterChip(selected = false, onClick = {}, label = { Text("Filter 2") })
                    FilterChip(selected = false, onClick = {}, label = { Text("Filter 3") })
                    FilterChip(selected = false, onClick = {}, label = { Text("Filter 4") })
                    Spacer(Modifier.width(16.dp))
                }
            }
        }
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
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun DoctorListPreview() {
    KalmakasaTheme {
    }
}