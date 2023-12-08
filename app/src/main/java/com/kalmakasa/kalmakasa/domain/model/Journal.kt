package com.kalmakasa.kalmakasa.domain.model

import com.kalmakasa.kalmakasa.common.Mood

data class Journal(
    val id: String,
    val mood: Mood,
    val date: String,
    val description: String,
)