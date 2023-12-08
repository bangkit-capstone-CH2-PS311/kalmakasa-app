package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kalmakasa.kalmakasa.domain.model.User

data class AuthResponse(

    @field:SerializedName("tokens")
    val tokens: Tokens,

    @field:SerializedName("user")
    val user: ApiUser,
)

data class Refresh(

    @field:SerializedName("expires")
    val expires: String,

    @field:SerializedName("token")
    val token: String,
)

data class ApiUser(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("isEmailVerified")
    val isEmailVerified: Boolean,

    @field:SerializedName("email")
    val email: String,
)

data class Access(

    @field:SerializedName("expires")
    val expires: String,

    @field:SerializedName("token")
    val token: String,
)

data class Tokens(
    @field:SerializedName("access")
    val access: Access,

    @field:SerializedName("refresh")
    val refresh: Refresh,
)

fun AuthResponse.toUser() = User(
    id = user.id,
    name = user.name,
    email = user.email,
    accessToken = tokens.access.token,
    refreshToken = tokens.refresh.token,
    isLogin = true,
)


