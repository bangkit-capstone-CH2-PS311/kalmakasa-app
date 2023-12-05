package com.kalmakasa.kalmakasa.presentation.state

import com.kalmakasa.kalmakasa.domain.model.User

sealed class SessionState {
    object Loading : SessionState()
    object NotLoggedIn : SessionState()
    data class LoggedIn(val session: User) : SessionState()
}