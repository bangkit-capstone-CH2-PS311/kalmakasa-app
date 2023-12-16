package com.kalmakasa.kalmakasa.presentation.screens.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import com.kalmakasa.kalmakasa.presentation.screens.auth.register.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _message = MutableStateFlow("")

    val loginState = combine(_isLoading, _isError, _message) { loading, error, message ->
        AuthState(loading, error, message)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AuthState()
    )

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.login(email, password).collect { result ->
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