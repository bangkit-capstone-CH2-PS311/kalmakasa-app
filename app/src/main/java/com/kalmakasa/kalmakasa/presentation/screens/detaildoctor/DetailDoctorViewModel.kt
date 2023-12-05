package com.kalmakasa.kalmakasa.presentation.screens.detaildoctor

import androidx.lifecycle.ViewModel
import com.kalmakasa.kalmakasa.common.util.ConsultationDate
import java.util.Calendar

class DetailDoctorViewModel : ViewModel() {

    private val calendar = Calendar.getInstance()
    val currentTime = calendar.timeInMillis

    val timeSlots = listOf("09.00", "10.00", "13.00", "14.00", "16.00", "17.00", "19.00")

    fun getCurrentWeek(): MutableList<ConsultationDate> {
        val consultationDates = mutableListOf<ConsultationDate>()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        repeat(CONSULTATION_WEEKS * 7) {
            val date = calendar.get(Calendar.DAY_OF_MONTH)
            val day = DAYS[calendar.get(Calendar.DAY_OF_WEEK) - 1]
            consultationDates.add(
                ConsultationDate(
                    dateInMillis = calendar.timeInMillis,
                    date = date,
                    day = day
                )
            )

            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return consultationDates
    }

    companion object {
        const val CONSULTATION_WEEKS = 2

        val DAYS = listOf(
            "Min",
            "Sen",
            "Sel",
            "Rab",
            "Kam",
            "Jum",
            "Sab",
        )
    }
}