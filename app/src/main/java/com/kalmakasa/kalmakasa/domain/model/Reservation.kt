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
    val report: ReservationReport? = null,
)

data class ReservationReport(
    val commonIssues: String,
    val psychologicalDynamics: String,
    val recommendation: String,
    val triggers: String,
)