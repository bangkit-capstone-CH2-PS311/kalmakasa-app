package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.appointment_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAppointmentViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<Resource<List<Reservation>>> =
        MutableStateFlow(Resource.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAppointment()
    }

    private fun getAppointment() {
        viewModelScope.launch {
            reservationRepository.getReservations().collect {
                _uiState.value = it
            }
        }
    }
}