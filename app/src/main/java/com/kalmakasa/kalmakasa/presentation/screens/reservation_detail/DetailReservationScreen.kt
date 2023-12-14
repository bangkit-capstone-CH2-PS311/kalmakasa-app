package com.kalmakasa.kalmakasa.presentation.screens.reservation_detail

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar
import com.kalmakasa.kalmakasa.presentation.theme.OnPositive
import com.kalmakasa.kalmakasa.presentation.theme.Positive

@Composable
fun DetailReservationScreen(
    reservation: Reservation,
    navUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = stringResource(R.string.reservation_detail),
                onBackButtonClicked = navUp,
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Consultant Detail
            OutlinedCard(
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(R.drawable.placeholder_consultant_img),
                            contentDescription = null,
                            modifier = Modifier
                                .border(Dp.Hairline, Color.Gray, MaterialTheme.shapes.small)
                                .size(80.dp),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = reservation.consultant.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                            )
                            Text(
                                text = reservation.consultant.speciality,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Gray
                            )
                        }
                    }
                    Text(
                        text = stringResource(R.string.biography),
                        style = MaterialTheme.typography.titleMedium,

                        )
                    Text(
                        text = reservation.consultant.biography,
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5,
                    )
                }
            }

            // User Note
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.your_notes),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = reservation.notes,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Consultation Detail
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
                        OutlinedCard(
                            colors = CardDefaults.outlinedCardColors(
                                containerColor = Positive,
                                contentColor = OnPositive,
                            )
                        ) {
                            Text(
                                text = reservation.status,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
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
        }
    }
}
