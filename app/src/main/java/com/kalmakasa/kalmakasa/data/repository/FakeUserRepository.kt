package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.data.ResultState
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.model.PrefUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.random.Random


class FakeUserRepository(
    private val pref: UserPreferences,
) : UserRepository {
    override suspend fun login(email: String, password: String) = flow {
        emit(ResultState.Loading)
        // TODO: Make API Call to login
        delay(2000)
        val user = PrefUser(
            id = "123",
            name = email,
            token = "token",
            isLogin = true
        )
        if (Random.nextInt(1, 4) == 3) {
            emit(ResultState.Error("Login Error"))
            pref.logout()
        } else {
            emit(ResultState.Success("Login Success"))
            pref.setSession(user)
        }
    }

    override suspend fun register(name: String, email: String, password: String) = flow {
        emit(ResultState.Loading)
        // TODO: Make API Call to login
        delay(2000)
        val user = PrefUser(
            id = "123",
            name = email,
            token = "token",
            isLogin = true
        )
        if (Random.nextBoolean()) {
            // Error
            emit(ResultState.Error("Login Error"))
        } else {
            // Success
            emit(ResultState.Success("Login Success"))
        }
        pref.setSession(user)
    }


    override suspend fun logout() {
        pref.logout()
    }

    override fun getSession() = pref.getSession()
}