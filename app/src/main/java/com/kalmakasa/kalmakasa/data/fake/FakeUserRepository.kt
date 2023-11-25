package com.kalmakasa.kalmakasa.data.fake

import com.kalmakasa.kalmakasa.data.ResultState
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.model.User
import com.kalmakasa.kalmakasa.data.network.response.toSession
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class FakeUserRepository(
    private val pref: UserPreferences,
    private val apiService: ApiService,
) : UserRepository {
    override suspend fun login(email: String, password: String) = flow {
        emit(ResultState.Loading)
        val response = apiService.login(email, password)
        if (response.error) {
            emit(ResultState.Error(response.message))
        } else {
            response.userData?.let { pref.setSession(it.toSession()) }
            emit(ResultState.Success(response.message))
        }
    }.catch { emit(ResultState.Error(it.message ?: "Unknown Error")) }

    override suspend fun register(name: String, email: String, password: String) = flow {
        emit(ResultState.Loading)
        val response = apiService.login(email, password)
        if (response.error) {
            emit(ResultState.Success(response.message))
        } else {
            emit(ResultState.Error(response.message))
        }
    }.catch { emit(ResultState.Error(it.message ?: "Unknown Error")) }


    override suspend fun logout() {
        pref.logout()
    }

    override fun getSession(): Flow<User> = pref.getSession()

}