package com.kalmakasa.kalmakasa.ui.state

import com.kalmakasa.kalmakasa.data.model.User

sealed class SessionState {
    object Loading : SessionState()
    object NotLoggedIn : SessionState()
    data class LoggedIn(val session: User) : SessionState()
}