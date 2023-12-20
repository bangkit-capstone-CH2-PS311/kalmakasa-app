package com.kalmakasa.kalmakasa.common

sealed class Role(val role: String) {
    data object Consultant : Role("consultant")
    data object User : Role("user")
}