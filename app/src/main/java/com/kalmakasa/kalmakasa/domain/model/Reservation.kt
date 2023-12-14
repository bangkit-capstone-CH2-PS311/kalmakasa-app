package com.kalmakasa.kalmakasa.domain.model

data class Reservation(
    val id: String,
    val patient: Patient,
    val consultant: Consultant,
    val date: String,
    val time: String,
    val status: String,
    val notes: String = "",
)