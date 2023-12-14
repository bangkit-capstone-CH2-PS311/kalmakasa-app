package com.kalmakasa.kalmakasa.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kalmakasa.kalmakasa.common.ReservationStatus

@Composable
fun StatusChip(
    status: ReservationStatus,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = status.containerColor,
            contentColor = status.contentColor,
        ),
        modifier = modifier
    ) {
        Text(
            text = status.status,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}