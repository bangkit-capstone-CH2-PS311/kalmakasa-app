package com.kalmakasa.kalmakasa.common

import com.kalmakasa.kalmakasa.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object DateUtil {

    val DAYS = listOf(
        R.string.sunday_short,
        R.string.monday_short,
        R.string.tuesday_short,
        R.string.wednesday_short,
        R.string.thursday_short,
        R.string.friday_short,
        R.string.saturday_short,
    )

    fun formatApiDate(apiDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = inputFormat.parse(apiDate)

        return if (date != null) {
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            outputFormat.format(date)
        } else {
            apiDate
        }
    }

    fun getCurrentDateInISO(): String {
        // Get the current date and time
        val currentDate = Calendar.getInstance().time

        // Format it to ISO 8601 format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())

        return dateFormat.format(currentDate)
    }
}