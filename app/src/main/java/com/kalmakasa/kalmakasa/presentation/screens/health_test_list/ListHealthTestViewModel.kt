package com.kalmakasa.kalmakasa.presentation.screens.health_test_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import com.kalmakasa.kalmakasa.domain.repository.HealthTestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHealthTestViewModel @Inject constructor(
    private val healthTestRepository: HealthTestRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<Resource<List<HealthTestResult>>> =
        MutableStateFlow(Resource.Loading)

    val uiState = _uiState.asStateFlow()

    init {
        getHealthTestResults()
    }

    private fun getHealthTestResults() {
        viewModelScope.launch {
            healthTestRepository.getHealthTests().collect {
                _uiState.value = it
            }
        }
    }
}