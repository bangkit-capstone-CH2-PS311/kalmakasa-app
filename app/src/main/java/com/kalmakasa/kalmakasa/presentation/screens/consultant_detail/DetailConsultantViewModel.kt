package com.kalmakasa.kalmakasa.presentation.screens.consultant_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.DateUtil.DAYS
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.domain.model.ConsultationDate
import com.kalmakasa.kalmakasa.domain.repository.ConsultantRepository
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailDoctorViewModel @Inject constructor(
    private val consultantRepository: ConsultantRepository,
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val timeSlots = listOf("10.00", "13.00", "14.00", "16.00", "17.00", "19.00")
    private val calendar = Calendar.getInstance()
    private val currentTime = calendar.timeInMillis

    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val bookState: MutableStateFlow<Resource<String>?> = MutableStateFlow(null)
    private val _consultant: MutableStateFlow<Consultant?> = MutableStateFlow(null)

    val uiState: StateFlow<DetailConsultantState> =
        combine(
            _isLoading,
            _isError,
            bookState,
            _consultant
        ) { loading, error, bookState, consultant ->
            DetailConsultantState(
                consultant,
                currentTime,
                timeSlots,
                getDates(),
                bookState,
                loading,
                error
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailConsultantState()
        )


    fun getDoctorDetail(id: String) {
        viewModelScope.launch {
            consultantRepository.getDoctorDetailById(id).collect { consultant ->
                when (consultant) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                        _consultant.value = consultant.data
                    }

                    else -> {
                        _isLoading.value = false
                        _isError.value = true
                    }
                }
            }
        }
    }

    fun createReservation(checkoutData: CheckoutData) {
        viewModelScope.launch {
            val userId = userRepository.getSession().first().id
            reservationRepository.createReservation(
                userId = userId,
                consultantId = checkoutData.consultant.id,
                profileId = checkoutData.consultant.profileId,
                date = DateUtil.millisToApi(checkoutData.dateInMillis),
                startTime = checkoutData.selectedTime,
                endTime = getEndTime(checkoutData.selectedTime),
                note = "note",
            ).collect { reservation ->
                bookState.value = reservation
            }
        }
    }

    private fun getDates(): MutableList<ConsultationDate> {
        val consultationDates = mutableListOf<ConsultationDate>()

        val calendar = Calendar.getInstance()
        repeat(14) {
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

    private fun getEndTime(startTime: String): String {
        val parts = startTime.split(".")
        val numericPart = parts[0].toInt() + 1

        return "$numericPart.${parts[1]}"
    }
}

data class DetailConsultantState(
    val consultant: Consultant? = null,
    val currentTime: Long = 0,
    val timeSlots: List<String> = emptyList(),
    val dates: List<ConsultationDate> = emptyList(),
    val bookState: Resource<String>? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)


data class CheckoutData(
    val consultant: Consultant,
    val dateInMillis: Long,
    val selectedTime: String,
    val userNote: String,
)