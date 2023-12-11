package com.kalmakasa.kalmakasa.domain.model

data class Consultant(
    val id: String,
    val name: String = "",
    val speciality: String = "",
    val expertise: List<String> = emptyList(),
    val yearExperience: Int = 0,
    val patientCount: Int = 0,
    val biography: String = "",
)