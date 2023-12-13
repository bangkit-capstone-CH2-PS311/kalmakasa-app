@file:OptIn(ExperimentalLayoutApi::class)

package com.kalmakasa.kalmakasa.presentation.screens.consultant_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.rounded.BusinessCenter
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.domain.model.ConsultationDate
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@Composable
fun DetailConsultantScreen(
    uiState: DetailConsultantState,
    navUp: () -> Unit,
    onAppointmentBooked: (CheckoutData) -> Unit,
) {
    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.detail_consultant),
                onBackButtonClicked = navUp
            )
        }
    ) { paddingValues ->
        when {
            uiState.isError -> {
                ErrorScreen(Modifier.padding(paddingValues))
            }

            uiState.isLoading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            uiState.consultant == null -> {
                Text(text = stringResource(R.string.data_not_found))
            }

            else -> {
                DetailConsultantContent(
                    uiState.consultant,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailConsultantContent(
    consultant: Consultant,
    timeSlots: List<String>,
    currentTime: Long,
    dates: List<ConsultationDate>,
    onAppointmentBooked: (CheckoutData) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedDate by rememberSaveable { mutableLongStateOf(-1) }
    var selectedTime by rememberSaveable { mutableStateOf("") }
    var userNote by rememberSaveable { mutableStateOf("") }

    var isCheckout by rememberSaveable { mutableStateOf(false) }

    val isValidated =
        (selectedDate != (-1).toLong() && selectedTime.isNotEmpty() && userNote.isNotEmpty())

    val checkoutData = CheckoutData(
        consultant = consultant,
        dateInMillis = selectedDate,
        selectedTime = selectedTime,
        userNote = userNote,
    )

    CheckoutDialog(
        checkoutData = checkoutData,
        showDialog = isCheckout,
        onDismissRequest = { isCheckout = false },
        onCheckout = onAppointmentBooked,
    )

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
                painter = painterResource(R.drawable.placeholder_consultant_img),
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
                    text = consultant.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )
                Text(
                    text = consultant.speciality,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )

                DoctorPerks(
                    title = stringResource(R.string.experience),
                    value = stringResource(R.string.experience_year, consultant.yearExperience),
                    icon = Icons.Rounded.BusinessCenter,
                    modifier = Modifier.padding(top = 8.dp)
                )
                DoctorPerks(
                    title = stringResource(R.string.patients),
                    value = consultant.patientCount.toString(),
                    icon = Icons.Rounded.Person,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // expertise
        if (consultant.expertise.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {

                TitleText(stringResource(R.string.expertise))
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    consultant.expertise.forEach {
                        OutlinedCard {
                            Text(
                                text = it,
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
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
                text = consultant.biography,
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
                    selected = consultationDate.dateInMillis == selectedDate,
                    enabled = consultationDate.dateInMillis > currentTime,
                    onClick = {
                        selectedDate = consultationDate.dateInMillis
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
            text = stringResource(R.string.your_note),
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = userNote,
            onValueChange = { userNote = it },
            maxLines = 5,
            placeholder = { Text(stringResource(R.string.hint_consultant_note)) },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(Modifier.height(32.dp))

        // Book Session
        Button(
            onClick = {
                isCheckout = true
            },
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
                text = stringResource(consultationDate.day),
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
        fontWeight = FontWeight.Medium,
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
        DetailConsultantScreen(
            DetailConsultantState(
                consultant = Consultant(
                    id = "dsada",
                    name = "Dzaky Nashshar",
                    speciality = "Kanker",
                    yearExperience = 2,
                    patientCount = 110,
                    biography = "lorem test",
                ),
                timeSlots = listOf("08.00", "09.00", "10.00", "11.00", "12.00"),
                dates = listOf()
            ),
            {},
            {},
        )
    }
}



