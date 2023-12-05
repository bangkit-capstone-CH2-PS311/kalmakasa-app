package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(email: String, password: String): Flow<Resource<String>>
    suspend fun register(name: String, email: String, password: String): Flow<Resource<String>>
    suspend fun logout()
    fun getSession(): Flow<User>
}