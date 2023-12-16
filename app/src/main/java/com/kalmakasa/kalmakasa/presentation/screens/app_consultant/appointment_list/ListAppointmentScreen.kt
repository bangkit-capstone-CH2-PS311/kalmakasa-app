package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_list

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kalmakasa.kalmakasa.common.ReservationStatus
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.StatusChip

@Composable
fun ListAppointmentScreen(
    uiState: Resource<List<Reservation>>,
    onAppointmentClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold { paddingValues ->
        when (uiState) {
            Resource.Loading -> {
                LoadingScreen(modifier.padding(paddingValues))
            }

            is Resource.Error -> {
                ErrorScreen(modifier = modifier.padding(paddingValues), uiState.error)
            }

            is Resource.Success -> {
                ListAppointmentContent(
                    appointments = uiState.data,
                    onAppointmentClicked = onAppointmentClicked,
                    modifier.padding(paddingValues)
                )
            }
        }

    }
}

@Composable
fun ListAppointmentContent(
    appointments: List<Reservation>,
    onAppointmentClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 24.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(appointments, key = { it.id }) { appointment ->
            AppointmentCard(
                reservationId = appointment.id,
                patientName = appointment.patient.name,
                imageUrl = appointment.consultant.imageUrl,
                status = appointment.status,
                date = appointment.date,
                time = appointment.time,
                onAppointmentClicked = onAppointmentClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentCard(
    reservationId: String,
    patientName: String,
    imageUrl: String,
    status: ReservationStatus,
    date: String,
    time: String,
    onAppointmentClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        border = BorderStroke(1.dp, Color.Gray),
        onClick = { onAppointmentClicked(reservationId) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier,
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(BorderStroke(1.dp, Color.Gray), CircleShape)
                        .size(54.dp),
                )
                Column(
                    modifier = Modifier
                        .weight(1f)

                ) {
                    Text(
                        text = patientName,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                StatusChip(status = status)
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
                        text = date,
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
                        text = time,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}