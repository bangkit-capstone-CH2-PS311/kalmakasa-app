package com.kalmakasa.kalmakasa.data.network.retrofit

import com.kalmakasa.kalmakasa.data.network.response.AuthResponse
import com.kalmakasa.kalmakasa.data.network.response.DoctorResponse
import com.kalmakasa.kalmakasa.data.network.response.ListDoctorResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse

    @GET("doctors")
    suspend fun getListDoctor(): ListDoctorResponse

    @GET("doctors/{id}")
    suspend fun getDoctorById(
        @Path("id") id: String,
    ): DoctorResponse
}