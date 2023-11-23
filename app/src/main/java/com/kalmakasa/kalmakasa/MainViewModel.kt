package com.kalmakasa.kalmakasa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.data.model.PrefUser
import com.kalmakasa.kalmakasa.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userRepository: UserRepository,
) : ViewModel() {
    val sessionState: StateFlow<SessionState> = userRepository.getSession().map {
        SessionState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = SessionState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )

}

sealed class SessionState {
    data class Success(val session: PrefUser) : SessionState()
    object Loading : SessionState()

}
