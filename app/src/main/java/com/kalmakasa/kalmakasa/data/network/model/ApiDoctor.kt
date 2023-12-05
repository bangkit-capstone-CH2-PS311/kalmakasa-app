package com.kalmakasa.kalmakasa.data.network.model

import com.kalmakasa.kalmakasa.domain.model.Doctor

data class ApiDoctor(
    val id: String,
    val name: String,
    val specialist: String,
    val expYear: Int,
    val patients: Int,
    val biography: String,
)


fun ApiDoctor.toDoctor() = Doctor(
    id = this.id,
    name = this.name,
    speciality = specialist,
    yearExperience = expYear,
    patientCount = patients,
    biography = biography,
)