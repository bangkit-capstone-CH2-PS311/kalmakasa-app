package com.kalmakasa.kalmakasa.domain.model

data class Consultant(
    val id: String,
    val name: String = "",
    val speciality: String = "",
    val imageUrl: String = "https://www.pacificsantacruzvet.com/files/santa_cruz_vet/female-placeholder.jpg",
    val expertise: List<String> = emptyList(),
    val yearExperience: Int = 0,
    val patientCount: Int = 0,
    val biography: String = "",
)