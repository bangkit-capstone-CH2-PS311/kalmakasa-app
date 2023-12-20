package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.common.Role
import com.kalmakasa.kalmakasa.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(email: String, password: String): Flow<Resource<Role>>
    fun register(name: String, email: String, password: String): Flow<Resource<String>>
    suspend fun logout()
    fun getSession(): Flow<User>
}