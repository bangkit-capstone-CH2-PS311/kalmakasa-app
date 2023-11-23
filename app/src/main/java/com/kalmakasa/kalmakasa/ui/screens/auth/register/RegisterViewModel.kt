package com.kalmakasa.kalmakasa.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.data.ResultState
import com.kalmakasa.kalmakasa.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _registerState = MutableStateFlow<ResultState<String>>(ResultState.None)
    val registerState = _registerState.asStateFlow()
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.register(name, email, password).collect {
                _registerState.value = it
            }
        }
    }
}