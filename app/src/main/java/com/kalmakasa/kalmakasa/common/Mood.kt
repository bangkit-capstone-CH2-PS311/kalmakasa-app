package com.kalmakasa.kalmakasa.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kalmakasa.kalmakasa.R

enum class Mood(
    @StringRes val stringRes: Int,
    @DrawableRes val iconRes: Int,
) {
    VERY_SAD(R.string.very_sad, R.drawable.very_sad),
    SAD(R.string.sad, R.drawable.sad),
    FLAT(R.string.flat, R.drawable.flat),
    HAPPY(R.string.happy, R.drawable.happy),
    VERY_HAPPY(R.string.very_happy, R.drawable.very_happy);

    companion object {
        fun intToMood(value: Int): Mood {
            return when (value) {
                1 -> VERY_SAD
                2 -> SAD
                3 -> FLAT
                4 -> HAPPY
                5 -> VERY_HAPPY
                else -> FLAT
            }
        }

    }
}


