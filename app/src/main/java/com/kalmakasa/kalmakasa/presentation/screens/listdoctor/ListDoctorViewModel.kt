package com.kalmakasa.kalmakasa.presentation.screens.listdoctor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Doctor
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

    private val _listDoctor = MutableStateFlow<Resource<List<Doctor>>>(Resource.Loading)

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<ListDoctorState> =
        combine(_listDoctor, _searchQuery) { listDoctor, query ->
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
                        listDoctor = listDoctor.data
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
            doctorRepository.getListDoctor().collect {
                _listDoctor.value = it
            }
        }
    }
}

data class ListDoctorState(
    val listDoctor: List<Doctor> = emptyList(),
    val searchQuery: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)