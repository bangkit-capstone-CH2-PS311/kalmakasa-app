package com.kalmakasa.kalmakasa.data.network.model.fake

import com.kalmakasa.kalmakasa.data.network.model.ApiDoctor

data class DoctorResponse(
    val error: Boolean,
    val message: String,
    val doctor: ApiDoctor? = null,
)
