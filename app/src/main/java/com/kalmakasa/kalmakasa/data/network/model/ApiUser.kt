package com.kalmakasa.kalmakasa.data.network.model

import com.kalmakasa.kalmakasa.domain.model.User

data class ApiUser(
    val name: String,
    val userId: String,
    val token: String,
)

fun ApiUser.toUser() = User(
    id = this.userId,
    name = this.name,
    token = this.token,
    isLogin = true
)