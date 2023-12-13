package com.kalmakasa.kalmakasa.presentation.screens.consultant_detail

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar

@Composable
fun CheckoutDialog(
    showDialog: Boolean,
    checkoutData: CheckoutData,
    onDismissRequest: () -> Unit,
    onCheckout: (CheckoutData) -> Unit = {},
) {
    if (showDialog) {
        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDismissRequest,
        ) {
            val context = LocalContext.current

            Scaffold(
                topBar = {
                    TitleTopAppBar(
                        title = stringResource(R.string.reservation_summary),
                        onBackButtonClicked = onDismissRequest
                    )
                }
            ) { paddingValues ->
                Column(
                    Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        AsyncImage(
                            model = checkoutData.consultant.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .width(72.dp)
                                .height(80.dp)
                                .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                text = checkoutData.consultant.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium,
                                fontSize = 18.sp,
                            )
                            Text(
                                text = checkoutData.consultant.speciality,
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Gray
                            )
                        }
                    }
                    Divider()
                    TitleText(stringResource(R.string.reservation_detail))
                    Column(
                        Modifier.padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                            Text(
                                stringResource(
                                    R.string.date,
                                    DateUtil.millisToDate(checkoutData.dateInMillis)
                                )
                            )
                        }
                        Row(horizontalArrangement = Arrangement.Center) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                            Text(stringResource(R.string.time_checkout, checkoutData.selectedTime))
                        }
                    }
                    Divider()
                    TitleText(stringResource(R.string.your_note))
                    Text(
                        text = checkoutData.userNote,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Button(
                        onClick = {
                            onCheckout(checkoutData)
                            onDismissRequest()
                            Toast.makeText(
                                context,
                                context.getString(R.string.reservation_created_successfully),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(stringResource(R.string.create_reservation))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
