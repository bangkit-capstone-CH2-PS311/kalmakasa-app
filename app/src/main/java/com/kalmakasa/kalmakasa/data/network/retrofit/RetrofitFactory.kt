package com.kalmakasa.kalmakasa.data.network.retrofit

import com.kalmakasa.kalmakasa.common.BASE_URL
import com.kalmakasa.kalmakasa.common.ML_URL
import com.kalmakasa.kalmakasa.data.UserPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun makeBackend(pref: UserPreferences): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = AuthInterceptor(pref)
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(ApiService::class.java)
    }

    fun makeMachineLearning(): MachineLearningService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(ML_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(MachineLearningService::class.java)
    }
}