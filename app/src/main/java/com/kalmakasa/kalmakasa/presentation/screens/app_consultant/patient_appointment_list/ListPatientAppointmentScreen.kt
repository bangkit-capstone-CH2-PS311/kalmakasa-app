package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_appointment_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar
import com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_list.AppointmentCard

@Composable
fun ListPatientAppointmentScreen(
    uiState: Resource<List<Reservation>>,
    onAppointmentClicked: (String) -> Unit,
    navUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.appointment_list),
                onBackButtonClicked = navUp,
            )
        }
    ) { paddingValues ->
        when (uiState) {
            Resource.Loading -> {
                LoadingScreen(modifier.padding(paddingValues))
            }

            is Resource.Error -> {
                ErrorScreen(modifier = modifier.padding(paddingValues), uiState.error)
            }

            is Resource.Success -> {
                ListPatientAppointmentContent(
                    appointments = uiState.data,
                    onAppointmentClicked = onAppointmentClicked,
                    modifier.padding(paddingValues)
                )
            }
        }

    }
}

@Composable
fun ListPatientAppointmentContent(
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
                imageUrl = appointment.patient.imageUrl,
                status = appointment.status,
                date = appointment.date,
                time = appointment.time,
                onAppointmentClicked = onAppointmentClicked
            )
        }
    }
}