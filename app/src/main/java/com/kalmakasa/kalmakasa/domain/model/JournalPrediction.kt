package com.kalmakasa.kalmakasa.domain.model

import com.kalmakasa.kalmakasa.common.Tag

data class JournalPrediction(
    val moodText: String = "",
    val sliderValue: Float = 2f,
    val tag: Tag? = null,
)