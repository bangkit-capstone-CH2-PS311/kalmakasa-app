package com.kalmakasa.kalmakasa.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import com.kalmakasa.kalmakasa.presentation.state.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
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
        if (it.isLogin) SessionState.LoggedIn(it) else SessionState.NotLoggedIn
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
