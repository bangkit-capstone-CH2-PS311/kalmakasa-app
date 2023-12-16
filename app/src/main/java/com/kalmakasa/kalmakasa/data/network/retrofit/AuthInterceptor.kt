package com.kalmakasa.kalmakasa.data.network.retrofit

import com.kalmakasa.kalmakasa.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val userPreferences: UserPreferences,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val session = runBlocking { userPreferences.getSession().first() }
        val modifierRequest = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${session.accessToken}")
            .build()

        return chain.proceed(modifierRequest)
    }
}