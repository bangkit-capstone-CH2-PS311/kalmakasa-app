package com.kalmakasa.kalmakasa.data.network

import com.kalmakasa.kalmakasa.data.network.response.AuthData
import com.kalmakasa.kalmakasa.data.network.response.AuthResponse
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import kotlinx.coroutines.delay
import kotlin.random.Random

class FakeApiService : ApiService {
    override suspend fun login(email: String, password: String): AuthResponse {
        delay(500)
        return if (Random.nextInt(1, 4) == 3) {
            AuthResponse(true, "Login Error")
        } else {
            AuthResponse(
                false, "Login Success", AuthData(
                    "User Full Name",
                    "123",
                    "AjkdhasjhToken"
                )
            )

        }
    }

    override suspend fun register(name: String, email: String, password: String): AuthResponse {
        delay(500)
        return if (Random.nextInt(1, 4) == 3) {
            AuthResponse(true, "Register Error")
        } else {
            AuthResponse(
                false, "Register Success", AuthData(
                    "User Full Name",
                    "123",
                    "AjkdhasjhToken"
                )
            )
        }
    }
}