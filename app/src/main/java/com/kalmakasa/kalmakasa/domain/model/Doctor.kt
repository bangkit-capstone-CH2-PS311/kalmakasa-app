package com.kalmakasa.kalmakasa.domain.model

data class Doctor(
    val id: String,
    val name: String,
    val speciality: String,
    val yearExperience: Int,
    val patientCount: Int,
    val biography: String,
)