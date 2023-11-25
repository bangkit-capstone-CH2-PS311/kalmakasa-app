package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.data.ResultState
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random


class FakeUserRepository(
    private val pref: UserPreferences,
) : UserRepository {
    override suspend fun login(email: String, password: String) = flow {
        emit(ResultState.Loading)
        // TODO: Make API Call to login
        delay(2000)

        val user = User(
            id = "123",
            name = email,
            token = "token",
            isLogin = true,
        )
        if (Random.nextInt(1, 4) == 3) {
            emit(ResultState.Error("Login Error"))
            pref.logout()
        } else {
            pref.setSession(user)
            emit(ResultState.Success("Login Success"))
        }
    }

    override suspend fun register(name: String, email: String, password: String) = flow {
        emit(ResultState.Loading)
        // TODO: Make API Call to login
        delay(2000)
        val user = User(
            id = "123",
            name = email,
            token = "token",
            isLogin = true,
        )
        if (Random.nextInt(1, 4) == 3) {
            // Error
            emit(ResultState.Error("Register Error"))
        } else {
            // Success
            pref.setSession(user)
            emit(ResultState.Success("Register Success"))
        }
    }


    override suspend fun logout() {
        pref.logout()
    }

    override fun getSession(): Flow<User> = pref.getSession()
}