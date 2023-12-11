package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.UserPreferences
import com.kalmakasa.kalmakasa.data.network.response.toUser
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.User
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException


class UserRepositoryImpl(
    private val pref: UserPreferences,
    private val apiService: ApiService,
) : UserRepository {
    override fun login(email: String, password: String) = flow {
        emit(Resource.Loading)
        val response = apiService.login(email, password)
        pref.setSession(response.toUser())
        emit(Resource.Success("Sign In Success"))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }

    override fun register(name: String, email: String, password: String) = flow {
        emit(Resource.Loading)
        val response = apiService.register(name, email, password)
        pref.setSession(response.toUser())
        emit(Resource.Success("Register Success"))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }

    override suspend fun logout() {
        pref.logout()
    }

    override fun getSession(): Flow<User> = pref.getSession()

}