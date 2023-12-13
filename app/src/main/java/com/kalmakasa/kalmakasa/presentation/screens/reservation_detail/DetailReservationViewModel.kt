package com.kalmakasa.kalmakasa.presentation.screens.reservation_detail

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
class DetailReservationViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailReservationState())
    val uiState = _uiState.asStateFlow()


    fun getReservationDetail(id: String) {
        viewModelScope.launch {
            reservationRepository.getReservationDetail(id).collect { reservation ->
                when (reservation) {
                    Resource.Loading -> {
                        _uiState.value = DetailReservationState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value = DetailReservationState(
                            reservation = reservation.data
                        )
                    }

                    else -> {
                        _uiState.value = DetailReservationState(isError = true)
                    }
                }
            }
        }
    }
}

data class DetailReservationState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val reservation: Reservation? = null,
)