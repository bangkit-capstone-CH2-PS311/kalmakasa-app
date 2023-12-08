package com.kalmakasa.kalmakasa.common

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


object DateUtil {
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