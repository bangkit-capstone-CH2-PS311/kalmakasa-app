package com.kalmakasa.kalmakasa.presentation.screens.reservation_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.presentation.component.LoadingScreen
import com.kalmakasa.kalmakasa.presentation.theme.KalmakasaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListReservationScreen(
    uiState: Resource<List<Reservation>>,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = stringResource(R.string.reservations))
            })
        }
    ) { paddingValues ->

        when (uiState) {
            Resource.Loading -> {
                LoadingScreen(Modifier.padding(paddingValues))
            }

            is Resource.Success -> {
                ListReservationContent(Modifier.padding(paddingValues))
            }

            else -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error")
                }
            }
        }


    }
}

@Composable
fun ListReservationContent(
    modifier: Modifier = Modifier,
    reservations: List<Reservation> = emptyList(),
) {
    LazyColumn(modifier) {
        items(reservations, key = { it.id }) {
        }
    }
}

@Preview
@Composable
fun ListReservationPreview() {
    KalmakasaTheme {
        ListReservationScreen(
            Resource.Success(
                listOf(
                    Reservation(
                        "",
                        "",
                        "",
                        "29-12-2002",
                        "14.00 - 15.00",
                        "Success",
                    )
                )
            )
        )
    }
}