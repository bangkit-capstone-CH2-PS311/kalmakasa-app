package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_appointment_list

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
class ListPatientAppointmentViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<Resource<List<Reservation>>> =
        MutableStateFlow(Resource.Loading)
    val uiState = _uiState.asStateFlow()

    fun getAppointment(id: String) {
        viewModelScope.launch {
            reservationRepository.getReservationByPatient(id).collect {
                _uiState.value = it
            }
        }
    }
}