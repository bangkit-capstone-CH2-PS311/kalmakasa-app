package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
fun DetailAppointmentScreen(
    reservation: Resource<Reservation>,
    reportState: PatientReportState,
    onAnswerChange: (String) -> Unit,
    uploadReport: () -> Unit,
    prevStep: () -> Unit,
    nextStep: () -> Unit,
    navUp: () -> Unit,
) {


    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.appointment_detail),
                onBackButtonClicked = navUp,
            )
        }
    ) { paddingValues ->

        when (reservation) {
            Resource.Loading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            is Resource.Error -> {
                ErrorScreen(Modifier.padding(paddingValues))
            }

            is Resource.Success -> {
                var reportEnable by rememberSaveable { mutableStateOf(false) }

                DetailAppointmentContent(
                    reservation = reservation.data,
                    onCreateReportButtonClicked = { reportEnable = true },
                    modifier = Modifier.padding(paddingValues)
                )

                ReportDialog(
                    showDialog = reportEnable,
                    onDismissRequest = {
                        reportEnable = false
                    },
                    onAnswerChange = onAnswerChange,
                    reportState = reportState,
                    uploadReport = uploadReport,
                    prevStep = prevStep,
                    nextStep = nextStep,
                )
            }
        }

    }
}

@Composable
fun DetailAppointmentContent(
    reservation: Reservation,
    onCreateReportButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = reservation.patient.name,
                        error = painterResource(R.drawable.placeholder_consultant_img),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape),
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = reservation.patient.name,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.patient_note),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = reservation.notes,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.consultation_detail),
                        style = MaterialTheme.typography.titleMedium
                    )
                    StatusChip(status = ReservationStatus.Pending)
                }
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
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        // TODO: Join Google Meet
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.join_meet),
                    )
                }
            }
        }
        if (reservation.report != null) {
            ReportCard(
                title = "Common Issues",
                content = reservation.report.commonIssues
            )
            ReportCard(
                title = "Psychological Dynamics",
                content = reservation.report.psychologicalDynamics
            )
            ReportCard(
                title = "Triggers",
                content = reservation.report.triggers
            )
            ReportCard(
                title = "Recommendation",
                content = reservation.report.recommendation
            )
            Button(
                onClick = onCreateReportButtonClicked,
                modifier = Modifier
                    .height(48.dp)
            ) {
                Text(
                    text = "Edit Report",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
        } else {
            Button(
                onClick = onCreateReportButtonClicked,
                modifier = Modifier
                    .height(48.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = stringResource(R.string.create_report),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}


@Composable
fun ReportCard(
    title: String,
    content: String,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = content, style = MaterialTheme.typography.bodyMedium)
        }
    }
}