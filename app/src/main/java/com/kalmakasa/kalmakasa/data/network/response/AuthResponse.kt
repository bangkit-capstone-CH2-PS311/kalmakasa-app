package com.kalmakasa.kalmakasa.data.network.response

import com.kalmakasa.kalmakasa.domain.model.User

data class AuthResponse(
    val error: Boolean,
    val message: String,
    val userData: AuthData? = null,
)

data class AuthData(
    val name: String,
    val userId: String,
    val token: String,
)

fun AuthData.toSession(): User = User(
    id = this.userId,
    name = this.name,
    token = this.token,
    isLogin = true
)
