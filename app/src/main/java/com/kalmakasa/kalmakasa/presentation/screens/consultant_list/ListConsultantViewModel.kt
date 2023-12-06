package com.kalmakasa.kalmakasa.presentation.screens.consultant_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.domain.repository.ConsultantRepository
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
    private val consultantRepository: ConsultantRepository,
) : ViewModel() {

    private val _listConsultant = MutableStateFlow<Resource<List<Consultant>>>(Resource.Loading)

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<ListConsultantState> =
        combine(_listConsultant, _searchQuery) { listConsultant, query ->
            when (listConsultant) {
                is Resource.Loading -> {
                    ListConsultantState(
                        searchQuery = query,
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    ListConsultantState(
                        searchQuery = query,
                        listConsultant = listConsultant.data
                    )
                }

                else -> {
                    ListConsultantState(
                        searchQuery = query,
                        isError = true
                    )
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListConsultantState()
        )

    init {
        getListConsultant()
    }

    private fun getListConsultant() {
        viewModelScope.launch {
            consultantRepository.getListConsultant().collect {
                _listConsultant.value = it
            }
        }
    }
}

data class ListConsultantState(
    val listConsultant: List<Consultant> = emptyList(),
    val searchQuery: String = "",
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)