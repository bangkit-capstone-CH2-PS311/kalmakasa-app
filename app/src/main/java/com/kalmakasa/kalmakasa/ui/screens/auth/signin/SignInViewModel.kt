package com.kalmakasa.kalmakasa.ui.screens.auth.signin

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
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<ResultState<String>>(ResultState.None)
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.login(email, password).collect {
                _loginState.value = it
            }
        }
    }
}