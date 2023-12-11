package com.kalmakasa.kalmakasa.domain.model

import androidx.annotation.StringRes

data class ConsultationDate(
    val dateInMillis: Long,
    val date: Int,
    @StringRes val day: Int,
)
