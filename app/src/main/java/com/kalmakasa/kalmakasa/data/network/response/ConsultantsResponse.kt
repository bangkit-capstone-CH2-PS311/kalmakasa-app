package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kalmakasa.kalmakasa.domain.model.Consultant

data class ConsultantsResponse(
    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("limit")
    val limit: Int,

    @field:SerializedName("totalPages")
    val totalPages: Int,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<ApiConsultant>,
)

data class ApiConsultant(

    @field:SerializedName("reviews")
    val reviews: List<String> = emptyList(),

    @field:SerializedName("patients")
    val patients: Int = 0,

    @field:SerializedName("rating")
    val rating: Int = 0,

    @field:SerializedName("bio")
    val bio: String = "",

    @field:SerializedName("fullname")
    val name: String = "",

    @field:SerializedName("id")
    val id: String = "",

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("experience")
    val experience: Int,

    @field:SerializedName("userId")
    val userId: String,
)

fun ApiConsultant.toConsultant() = Consultant(
    id = id,
    name = name,
    speciality = "Template Speciality",
    yearExperience = experience,
    patientCount = patients,
    biography = bio
)