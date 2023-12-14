package com.kalmakasa.kalmakasa.common

sealed class Role(val role: String) {
    object Consultant : Role("consultant")
    object User : Role("user")


}