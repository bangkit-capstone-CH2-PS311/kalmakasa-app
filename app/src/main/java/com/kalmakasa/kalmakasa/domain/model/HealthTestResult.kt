package com.kalmakasa.kalmakasa.domain.model

import androidx.room.Entity
import com.kalmakasa.kalmakasa.common.HealthTestType

@Entity
data class HealthTestResult(
    val id: String,
    val depression: HealthTestType.Depression,
    val anxiety: HealthTestType.Anxiety,
    val stress: HealthTestType.Stress,
    val date: String,
    val activeTag: List<String> = emptyList(),
)