package com.kalmakasa.kalmakasa.data.network.response

import com.kalmakasa.kalmakasa.data.network.model.ApiDoctor

data class ListDoctorResponse(
    val error: Boolean,
    val message: String,
    val listDoctor: List<ApiDoctor> = emptyList(),
)
