package com.kalmakasa.kalmakasa.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.ui.state.SessionState
import com.kalmakasa.kalmakasa.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val homeState = userRepository.getSession().map {
        delay(2000)
        if (it.isLogin) {
            SessionState.LoggedIn(it)
        } else {
            SessionState.NotLoggedIn
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SessionState.Loading
    )

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
