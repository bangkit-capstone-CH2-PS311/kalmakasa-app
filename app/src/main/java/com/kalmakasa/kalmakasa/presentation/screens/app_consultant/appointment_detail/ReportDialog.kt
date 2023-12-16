package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.presentation.component.DiscardDialog
import com.kalmakasa.kalmakasa.presentation.component.TitleTopAppBar

@Composable
fun ReportDialog(
    showDialog: Boolean,
    reportState: PatientReportState,
    onAnswerChange: (String) -> Unit,
    prevStep: () -> Unit,
    nextStep: () -> Unit,
    uploadReport: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    if (showDialog) {
        LaunchedEffect(reportState.uploadState) {
            when (reportState.uploadState) {
                is Resource.Error -> {
                    Toast.makeText(context, reportState.uploadState.error, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Success -> {
                    Toast.makeText(context, reportState.uploadState.data, Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {}
            }
        }

        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDismissRequest,
        ) {
            var enable by rememberSaveable { mutableStateOf(false) }

            DiscardDialog(
                enable = enable,
                onDismissRequest = {
                    enable = false
                },
                onConfirmation = {
                    onDismissRequest()
                }
            )

            Scaffold(
                topBar = {
                    Column {
                        TitleTopAppBar(
                            title = "Patient Form",
                            onBackButtonClicked = {
                                enable = true
                            }
                        )
                        LinearProgressIndicator(
                            progress = reportState.progress,
                            modifier = Modifier
                                .padding(
                                    horizontal = 24.dp,
                                    vertical = 16.dp,
                                )
                                .height(8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth(),
                        )
                    }
                },
                bottomBar = {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = prevStep,
                            modifier = Modifier.weight(1f),
                            enabled = reportState.currentIndex > 0,
                        ) {
                            Text(text = stringResource(R.string.previous))
                        }
                        Button(
                            onClick = if (reportState.isFinal) uploadReport else nextStep,
                            modifier = Modifier.weight(1f),
                            enabled = reportState.currentAnswer.isNotEmpty() && !reportState.isUploading,
                        ) {
                            if (reportState.isFinal && reportState.isUploading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(20.dp),
                                    strokeWidth = 2.5.dp
                                )
                            } else if (reportState.isFinal) {
                                Text(stringResource(R.string.done))
                            } else {
                                Text(stringResource(R.string.next))
                            }
                        }
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Text(
                        text = reportState.currentQuestion,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                    )
                    OutlinedTextField(
                        value = reportState.currentAnswer,
                        onValueChange = onAnswerChange,
                        maxLines = 8,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp)
                            .fillMaxSize(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.insert_here),
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    )
                }
            }
        }
    }
}
