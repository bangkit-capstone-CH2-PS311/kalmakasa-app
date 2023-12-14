package com.kalmakasa.kalmakasa.domain.model

import com.kalmakasa.kalmakasa.common.ReservationStatus

data class Reservation(
    val id: String,
    val patient: Patient,
    val consultant: Consultant,
    val date: String,
    val time: String,
    val status: ReservationStatus,
    val notes: String = "",
)