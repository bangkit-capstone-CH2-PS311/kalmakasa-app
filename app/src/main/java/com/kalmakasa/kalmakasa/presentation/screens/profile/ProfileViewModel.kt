package com.kalmakasa.kalmakasa.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.domain.model.User
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(User())
    val uiState = _uiState.asStateFlow()

    init {
        getSession()
    }

    private fun getSession() {
        viewModelScope.launch {
            userRepository.getSession().collect {
                _uiState.value = it
            }
        }
    }

    fun logout(callback: () -> Unit) {
        viewModelScope.launch {
            userRepository.logout()
            callback()
        }
    }
}