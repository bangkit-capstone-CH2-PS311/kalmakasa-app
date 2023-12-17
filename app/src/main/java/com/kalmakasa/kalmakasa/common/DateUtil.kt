package com.kalmakasa.kalmakasa.common

import com.kalmakasa.kalmakasa.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


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

    fun formatApiDate(apiDate: String, pattern: String = "EEEE, dd MMMM yyyy"): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = inputFormat.parse(apiDate)

        return if (date != null) {
            val outputFormat = SimpleDateFormat(pattern, Locale.getDefault())
            outputFormat.format(date)
        } else {
            apiDate
        }
    }

    fun apiToDate(apiDate: String): Date? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return inputFormat.parse(apiDate)
    }

    fun millisToDate(date: Long): String {
        val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun millisToApi(date: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getCurrentDateInISO(): String {
        // Get the current date and time
        val currentDate = Calendar.getInstance().time

        // Format it to ISO 8601 format
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())

        return dateFormat.format(currentDate)
    }

    fun isDateToday(date: Date): Boolean {
        val calendar = Calendar.getInstance(TimeZone.getDefault())

        val dateCalendar = Calendar.getInstance(TimeZone.getDefault())
        dateCalendar.time = date

        return (dateCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && dateCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && dateCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))
    }
}