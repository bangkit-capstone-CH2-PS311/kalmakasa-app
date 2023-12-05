package com.kalmakasa.kalmakasa.presentation.screens.detaildoctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.common.util.ConsultationDate
import com.kalmakasa.kalmakasa.domain.model.Doctor
import com.kalmakasa.kalmakasa.domain.repository.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailDoctorViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository,
) : ViewModel() {

    private val timeSlots = listOf("09.00", "10.00", "13.00", "14.00", "16.00", "17.00", "19.00")
    private val calendar = Calendar.getInstance()
    private val currentTime = calendar.timeInMillis

    private val _uiState = MutableStateFlow(DetailDoctorState())
    val uiState: StateFlow<DetailDoctorState> = _uiState.asStateFlow()
    fun getDoctorDetail(id: String) {
        viewModelScope.launch {
            doctorRepository.getDoctorDetailById(id).collect { doctor ->
                when (doctor) {
                    is Resource.Loading -> {
                        _uiState.value = DetailDoctorState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value = DetailDoctorState(
                            doctor = doctor.data,
                            timeSlots = timeSlots,
                            currentTime = currentTime,
                            dates = getDates()
                        )
                    }

                    else -> {
                        _uiState.value = DetailDoctorState(isError = true)
                    }
                }
            }
        }
    }

    private fun getDates(): MutableList<ConsultationDate> {
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

data class DetailDoctorState(
    val doctor: Doctor? = null,
    val timeSlots: List<String> = emptyList(),
    val currentTime: Long = 0,
    val dates: List<ConsultationDate> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)