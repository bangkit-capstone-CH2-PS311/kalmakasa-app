package com.kalmakasa.kalmakasa.presentation.screens.consultant_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.EXPERTISES
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.domain.repository.ConsultantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListDoctorViewModel @Inject constructor(
    private val consultantRepository: ConsultantRepository,
) : ViewModel() {

    private val _listConsultant = MutableStateFlow<Resource<List<Consultant>>>(Resource.Loading)
    private val _filterChip = MutableStateFlow(EXPERTISES.associateWith { false })
    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<ListConsultantState> =
        combine(_listConsultant, _searchQuery, _filterChip) { listConsultant, query, filtersValue ->
            when (listConsultant) {
                is Resource.Loading -> {
                    ListConsultantState(
                        searchQuery = query,
                        isLoading = true,
                        filterValue = filtersValue
                    )
                }

                is Resource.Success -> {
                    val consultants =
                        if (query.isNotBlank()) {
                            listConsultant.data.filter {
                                it.name.contains(
                                    query,
                                    ignoreCase = true
                                )
                            }
                        } else {
                            listConsultant.data
                        }

                    val enabledFilters = _filterChip.value.filterValues { it }.keys.toList()
                    val filteredConsultants = if (enabledFilters.isNotEmpty()) {
                        consultants.filter {
                            enabledFilters.any { el -> it.expertise.contains(el.lowercase()) }
                        }
                    } else {
                        consultants
                    }

                    ListConsultantState(
                        searchQuery = query,
                        listConsultant = filteredConsultants,
                        filterValue = filtersValue
                    )
                }

                else -> {
                    ListConsultantState(
                        searchQuery = query,
                        isError = true,
                        filterValue = filtersValue
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

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onFilterClicked(filter: String) {
        _filterChip.update {
            val map = it.toMutableMap()
            map[filter] = !map.getValue(filter)
            map
        }
    }

    fun onForMeClicked() {
        // TODO : REMOVE THIS AFTER DASS DONE
        val userTag = listOf("Stress")
        _filterChip.update {
            EXPERTISES.associateWith {
                userTag.contains(it)
            }
        }
    }
}

data class ListConsultantState(
    val listConsultant: List<Consultant> = emptyList(),
    val searchQuery: String = "",
    val isError: Boolean = false,
    val filterValue: Map<String, Boolean> = emptyMap(),
    val isLoading: Boolean = false,
)