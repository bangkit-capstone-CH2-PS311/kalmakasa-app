package com.kalmakasa.kalmakasa.presentation.screens.detaildoctor

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.BusinessCenter
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.util.ConsultationDate
import com.kalmakasa.kalmakasa.domain.model.Doctor
import com.kalmakasa.kalmakasa.presentation.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDoctorScreen(
    uiState: DetailDoctorState,
    onBackButtonClicked: () -> Unit,
    onAppointmentBooked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 8.dp),
                title = {
                    Text(
                        text = "Detail Doctor",
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackButtonClicked) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Log.d("state", uiState.toString())
        when {
            uiState.isError -> {
                Text(text = "Error")
            }

            uiState.isLoading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            uiState.doctor == null -> {
                Text(text = "Doctor not found")
            }

            else -> {
                DetailDoctorContent(
                    uiState.doctor,
                    uiState.timeSlots,
                    uiState.currentTime,
                    uiState.dates,
                    onAppointmentBooked,
                    Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun DetailDoctorContent(
    doctor: Doctor,
    timeSlots: List<String>,
    currentTime: Long,
    dates: List<ConsultationDate>,
    onAppointmentBooked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedDate by rememberSaveable { mutableIntStateOf(0) }
    var selectedTime by rememberSaveable { mutableStateOf("") }
    var userNotes by rememberSaveable { mutableStateOf("") }

    val isValidated = (selectedDate != 0 && selectedTime.isNotEmpty() && userNotes.isNotEmpty())

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
    ) {
        // Doctor Section
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.placeholder_doctor_img),
                contentDescription = null,
                modifier = Modifier
                    .width(144.dp)
                    .height(160.dp)
                    .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )
                Text(
                    text = "Psychology Specialist",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )

                DoctorPerks(
                    title = "Experience",
                    value = "2 Year(s)",
                    icon = Icons.Rounded.BusinessCenter,
                    modifier = Modifier.padding(top = 8.dp)
                )
                DoctorPerks(
                    title = "Patients",
                    value = "215",
                    icon = Icons.Rounded.Person,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Biography Section
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            TitleText(stringResource(R.string.biography))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = doctor.biography,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        // Schedule Section
        Spacer(modifier = Modifier.height(8.dp))
        TitleText(
            text = stringResource(R.string.schedule_consultation),
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = dates, key = { it.dateInMillis }) { consultationDate ->
                ScheduleDate(
                    consultationDate = consultationDate,
                    selected = consultationDate.date == selectedDate,
                    enabled = consultationDate.dateInMillis > currentTime,
                    onClick = {
                        selectedDate = consultationDate.date
                    })

            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Time Section
        TitleText(
            text = stringResource(R.string.time),
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(32.dp)
        ) {
            items(items = timeSlots, key = null) { time ->
                ScheduleTime(
                    time = time,
                    selected = selectedTime == time,
                    enabled = true,
                    onClick = { selectedTime = time }
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        TitleText(
            text = "Your Notes",
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = userNotes,
            onValueChange = { userNotes = it },
            maxLines = 5,
            placeholder = { Text("insert your notes for the doctor here.") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(Modifier.height(32.dp))
        // Book Session
        Button(
            onClick = onAppointmentBooked,
            enabled = isValidated,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(48.dp)
        ) {
            Text(stringResource(R.string.book_appointment))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTime(
    time: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        enabled = enabled,
        colors = CardDefaults.cardColors(
            if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier,
    ) {
        Box(
            Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        ) { Text(time) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDate(
    consultationDate: ConsultationDate,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        enabled = enabled,
        colors = CardDefaults.cardColors(
            if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .width(60.dp)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = consultationDate.day,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = consultationDate.date.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TitleText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        modifier = modifier
    )
}

@Composable
fun DoctorPerks(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(end = 8.dp)
                .background(Color.LightGray, MaterialTheme.shapes.small)
                .padding(6.dp)
                .size(20.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun DetailDoctorPreview() {
    KalmakasaTheme {
        DetailDoctorScreen(
            DetailDoctorState(
                doctor = Doctor(
                    "dsada",
                    "Dzaky Nashshar",
                    "Kanker",
                    2,
                    100,
                    "lorem goblok"
                ),
                timeSlots = listOf("08.00", "09.00", "10.00", "11.00", "12.00"),
                dates = listOf(
                    ConsultationDate(200, 12, "Sen"),
                    ConsultationDate(201, 13, "Sel"),
                    ConsultationDate(202, 14, "Rab"),
                    ConsultationDate(203, 15, "Kam"),
                    ConsultationDate(204, 16, "Jum"),
                    ConsultationDate(205, 17, "Sab"),
                    ConsultationDate(206, 18, "Min"),
                )
            ),
            {},
            {},
        )
    }

}