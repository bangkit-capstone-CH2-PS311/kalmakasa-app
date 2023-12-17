package com.kalmakasa.kalmakasa.common

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.kalmakasa.kalmakasa.R

sealed class HealthTestType(
    @StringRes val name: Int,
    val scale: Float,
) {
    data class Depression(val score: Int) : HealthTestType(
        name = R.string.depression,
        scale = score.toFloat() / 21
    )

    data class Anxiety(val score: Int) : HealthTestType(
        name = R.string.anxiety,
        scale = score.toFloat() / 21
    )

    data class Stress(val score: Int) : HealthTestType(
        name = R.string.stress,
        scale = score.toFloat() / 21
    )

    companion object {
        fun getHealthTestScoring(type: HealthTestType): HealthTestLevel {
            return when (type) {
                is Anxiety -> {
                    when (type.score) {
                        in 0..4 -> HealthTestLevel.Normal
                        in 5..6 -> HealthTestLevel.Mild
                        in 7..10 -> HealthTestLevel.Moderate
                        in 11..13 -> HealthTestLevel.Severe
                        in 14..25 -> HealthTestLevel.ExtremelySevere
                        else -> HealthTestLevel.UnknownValue
                    }
                }

                is Depression -> {
                    when (type.score) {
                        in 0..3 -> HealthTestLevel.Normal
                        in 4..5 -> HealthTestLevel.Mild
                        in 6..7 -> HealthTestLevel.Moderate
                        in 8..9 -> HealthTestLevel.Severe
                        in 10..25 -> HealthTestLevel.ExtremelySevere
                        else -> HealthTestLevel.UnknownValue
                    }
                }

                is Stress -> {
                    when (type.score) {
                        in 0..7 -> HealthTestLevel.Normal
                        in 8..9 -> HealthTestLevel.Mild
                        in 10..12 -> HealthTestLevel.Moderate
                        in 13..16 -> HealthTestLevel.Severe
                        in 17..25 -> HealthTestLevel.ExtremelySevere
                        else -> HealthTestLevel.UnknownValue
                    }
                }
            }
        }
    }
}

enum class HealthTestLevel(
    @StringRes val desc: Int,
    val containerColor: Color,
    val contentColor: Color,
) {
    Normal(R.string.normal, Color(0xFF90DDA5), Color(0xFF0F7A1A)),
    Mild(R.string.mild, Color(0xFF0F9634), Color(0xFF8FE398)),
    Moderate(R.string.moderate, Color(0xFFEBE444), Color(0xFF718204)),
    Severe(R.string.severe, Color(0xFFFF83A1), Color(0xFFB22F4E)),
    ExtremelySevere(R.string.extremely_severe, Color(0xFFBF0B0B), Color(0xFFFFBDBD)),
    UnknownValue(R.string.unknown_value, Color.LightGray, Color.DarkGray)
}