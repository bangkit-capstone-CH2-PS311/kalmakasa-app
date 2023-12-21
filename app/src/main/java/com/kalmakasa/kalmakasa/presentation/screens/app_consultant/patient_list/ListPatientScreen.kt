package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Patient
import com.kalmakasa.kalmakasa.presentation.component.ErrorScreen
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar

@Composable
fun ListPatientScreen(
    patientState: Resource<List<Patient>>,
    navigateToAppointmentList: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TitleTopAppBar(title = stringResource(R.string.patient))
        }
    ) { paddingValues ->
        when (patientState) {
            is Resource.Error -> {
                ErrorScreen(Modifier.padding(paddingValues))
            }

            is Resource.Loading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                    items(patientState.data) {
                        PatientCard(
                            name = it.name,
                            imageUrl = it.imageUrl,
                            onCardClick = { navigateToAppointmentList(it.id) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun PatientCard(
    name: String,
    imageUrl: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onCardClick() }
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageUrl,
            error = painterResource(R.drawable.user_profile_placeholder),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape),
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}