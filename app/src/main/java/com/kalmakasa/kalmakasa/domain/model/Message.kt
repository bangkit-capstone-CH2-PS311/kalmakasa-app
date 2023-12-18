package com.kalmakasa.kalmakasa.domain.model

data class Message(
    val msg: String,
    val isUser: Boolean,
    val date: String = "",
)