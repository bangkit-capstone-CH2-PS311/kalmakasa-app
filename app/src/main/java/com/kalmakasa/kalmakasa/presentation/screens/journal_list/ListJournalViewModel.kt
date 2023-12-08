package com.kalmakasa.kalmakasa.presentation.screens.journal_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Journal
import com.kalmakasa.kalmakasa.domain.repository.JournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListJournalViewModel @Inject constructor(
    private val journalRepository: JournalRepository,
) : ViewModel() {

    private val _journals = MutableStateFlow(emptyList<Journal>())
    private val _isLoading = MutableStateFlow(true)
    private val _isError = MutableStateFlow(false)
    
    val uiState: StateFlow<ListJournalState> =
        combine(_isLoading, _isError, _journals) { loading, error, journals ->
            ListJournalState(loading, error, journals)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ListJournalState(isLoading = true)
        )

    init {
        getJournal()
    }

    private fun getJournal() {
        viewModelScope.launch {
            journalRepository.getJournals().collect { journals ->
                when (journals) {
                    Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                        _journals.value = journals.data
                    }

                    else -> {
                        _isLoading.value = false
                        _isError.value = false
                    }
                }
            }
        }
    }

}

data class ListJournalState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val listJournal: List<Journal> = emptyList(),

    )
