package com.kalmakasa.kalmakasa.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import com.kalmakasa.kalmakasa.presentation.state.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    userRepository: UserRepository,
) : ViewModel() {

    val sessionState = userRepository.getSession().map {
        delay(2000)
        if (it.isLogin) SessionState.LoggedIn(it) else SessionState.NotLoggedIn
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SessionState.Loading
    )
}
