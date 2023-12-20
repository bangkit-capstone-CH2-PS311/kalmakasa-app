package com.kalmakasa.kalmakasa.presentation.screens.app_consultant.patient_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Patient
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPatientViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
) : ViewModel() {
    private val _patientState: MutableStateFlow<Resource<List<Patient>>> =
        MutableStateFlow(Resource.Loading)
    val patientState = _patientState.asStateFlow()

    init {
        getPatients()
    }

    private fun getPatients() {
        viewModelScope.launch {
            reservationRepository.getReservationsPatients().collect { patients ->
                _patientState.value = patients
            }
        }
    }

}