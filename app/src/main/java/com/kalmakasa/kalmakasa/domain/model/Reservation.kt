package com.kalmakasa.kalmakasa.domain.model

data class Reservation(
    val id: String,
    val userId: String,
    val consultantName: String = "",
    val consultantImageUrl: String = "https://www.pacificsantacruzvet.com/files/santa_cruz_vet/female-placeholder.jpg",
    val consultantBio: String = "",
    val consultantSpeciality: String,
    val patientNote: String = "",
    val date: String,
    val time: String,
    val status: String,
)