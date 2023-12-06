package com.kalmakasa.kalmakasa.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
    val isLogin: Boolean = false,
)