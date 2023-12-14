package com.kalmakasa.kalmakasa.domain.model

data class Patient(
    val id: String,
    val name: String,
    val email: String,
    val isEmailVerified: Boolean,
)