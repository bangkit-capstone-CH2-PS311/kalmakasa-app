package com.kalmakasa.kalmakasa.presentation.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _registerState = MutableStateFlow<Resource<String>>(Resource.None)
    val registerState = _registerState.asStateFlow()
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.register(name, email, password).collect {
                _registerState.value = it
            }
        }
    }
}