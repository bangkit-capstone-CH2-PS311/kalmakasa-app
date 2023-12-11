package com.kalmakasa.kalmakasa.domain.model

data class Reservation(
    val id: String,
    val userId: String,
    val consultantId: String,
    val date: String,
    val time: String,
    val status: String,
)