package com.kalmakasa.kalmakasa.presentation.screens.reservation_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.ReservationStatus
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.StatusChip
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar

@Composable
fun ListReservationScreen(
    uiState: Resource<List<Reservation>>,
    onReservationClicked: (String) -> Unit,
) {
    val tabs = ReservationStatus.entries
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            Column {
                TitleTopAppBar(title = stringResource(R.string.reservations))
                TabRow(
                    selectedTabIndex = tabIndex,
                    Modifier.background(Color.Red)
                ) {
                    tabs.forEachIndexed { index, status ->
                        Tab(
                            text = { Text(text = status.status) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        when (uiState) {
            Resource.Loading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            is Resource.Success -> {
                ListReservationContent(
                    modifier = Modifier.padding(paddingValues),
                    reservations = uiState.data,
                    status = tabs[tabIndex],
                    onReservationClicked = onReservationClicked,
                )
            }

            is Resource.Error -> {
                ErrorScreen(Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun ListReservationContent(
    status: ReservationStatus,
    onReservationClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    reservations: List<Reservation> = emptyList(),
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            reservations.filter { it.status == status },
            key = { it.id }
        ) { reservation ->
            ReservationCard(
                reservation = reservation,
                onReservationClicked = onReservationClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationCard(
    reservation: Reservation,
    onReservationClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        border = BorderStroke(1.dp, Color.Gray),
        onClick = { onReservationClicked(reservation.id) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AsyncImage(
                    model = reservation.consultant.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color.Gray),
                            MaterialTheme.shapes.small
                        )
                        .size(64.dp),
                )
                Column(
                    modifier = Modifier
                        .weight(1f)

                ) {
                    Text(
                        text = reservation.consultant.name,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = reservation.consultant.speciality,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                StatusChip(status = reservation.status)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        modifier = Modifier
                            .background(Color.LightGray, MaterialTheme.shapes.small)
                            .padding(4.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = reservation.date,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier
                            .background(Color.LightGray, MaterialTheme.shapes.small)
                            .padding(4.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = reservation.time,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}