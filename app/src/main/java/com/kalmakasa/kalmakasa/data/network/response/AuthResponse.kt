package com.kalmakasa.kalmakasa.data.network.response

import com.kalmakasa.kalmakasa.data.network.model.ApiUser

data class AuthResponse(
    val error: Boolean,
    val message: String,
    val userData: ApiUser? = null,
)

