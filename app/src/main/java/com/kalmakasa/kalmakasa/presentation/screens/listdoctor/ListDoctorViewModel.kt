package com.kalmakasa.kalmakasa.presentation.screens.listdoctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.domain.repository.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListDoctorViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository,
) : ViewModel() {

    private val _listConsultant = MutableStateFlow<Resource<List<Consultant>>>(Resource.Loading)

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<ListDoctorState> =
        combine(_listConsultant, _searchQuery) { listDoctor, query ->
            when (listDoctor) {
                is Resource.Loading -> {
                    ListDoctorState(
                        searchQuery = query,
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    ListDoctorState(
                        searchQuery = query,
                        listConsultant = listDoctor.data
                    )
                }

                else -> {
                    ListDoctorState(
                        searchQuery = query,
                        isError = true
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListDoctorState()
        )

    init {
        getListDoctor()
    }

    private fun getListDoctor() {
        viewModelScope.launch {
            doctorRepository.getListConsultant().collect {
                _listConsultant.value = it
            }
        }
    }
}

data class ListDoctorState(
    val listConsultant: List<Consultant> = emptyList(),
    val searchQuery: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)