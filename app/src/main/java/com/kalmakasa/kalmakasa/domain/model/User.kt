package com.kalmakasa.kalmakasa.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val token: String = "",
    val isLogin: Boolean = false,
)