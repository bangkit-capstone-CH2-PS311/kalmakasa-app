package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.data.ResultState
import com.kalmakasa.kalmakasa.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): Flow<ResultState<String>>
    suspend fun register(name: String, email: String, password: String): Flow<ResultState<String>>
    suspend fun logout()
    fun getSession(): Flow<User>
}