package com.kalmakasa.kalmakasa.presentation.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.common.Role
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _message = MutableStateFlow("")

    val registerState = combine(_isLoading, _isError, _message) { loading, error, message ->
        AuthState(loading, error, message)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AuthState()
    )

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.register(name, email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                        _isError.value = false
                        _message.value = ""
                    }

                    is Resource.Error -> {
                        _isLoading.value = false
                        _isError.value = true
                        _message.value = result.error
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                        _isError.value = false
                        _message.value = result.data
                    }
                }
            }
        }
    }
}

data class AuthState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
    val role: Role? = null,
)