package com.kalmakasa.kalmakasa.common

import androidx.compose.ui.graphics.Color
import com.kalmakasa.kalmakasa.presentation.theme.OnPositive
import com.kalmakasa.kalmakasa.presentation.theme.Positive

enum class ReservationStatus(
    val status: String,
    val containerColor: Color,
    val contentColor: Color,
) {
    Pending(
        "Pending",
        Color(0xFFF3D878),
        Color(0xFF9B7D11)
    ),
    Completed(
        "Completed",
        Positive,
        OnPositive
    );

    companion object {
        fun getStatus(status: String): ReservationStatus =
            when (status) {
                "pending" -> Pending
                "completed" -> Completed
                else -> Pending
            }
    }
}
