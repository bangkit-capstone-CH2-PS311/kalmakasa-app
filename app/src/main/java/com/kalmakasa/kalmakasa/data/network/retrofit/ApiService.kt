package com.kalmakasa.kalmakasa.data.network.retrofit

import com.kalmakasa.kalmakasa.data.network.response.ApiConsultant
import com.kalmakasa.kalmakasa.data.network.response.ApiJournal
import com.kalmakasa.kalmakasa.data.network.response.ApiReservation
import com.kalmakasa.kalmakasa.data.network.response.AuthResponse
import com.kalmakasa.kalmakasa.data.network.response.ConsultantsResponse
import com.kalmakasa.kalmakasa.data.network.response.CreateReservationResponse
import com.kalmakasa.kalmakasa.data.network.response.JournalsResponse
import com.kalmakasa.kalmakasa.data.network.response.ReservationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse

    @GET("consultants")
    suspend fun getListConsultants(): ConsultantsResponse

    @GET("consultants/{id}")
    suspend fun getConsultantDetail(
        @Path("id") id: String,
    ): ApiConsultant

    @GET("journals")
    suspend fun getJournals(): JournalsResponse

    @GET("journals/{id}")
    suspend fun getJournalDetail(
        @Path("id") id: String,
    ): ApiJournal

    @FormUrlEncoded
    @POST("journals")
    suspend fun createJournal(
        @Field("userId") id: String,
        @Field("date") date: String,
        @Field("title") title: String = "",
        @Field("content") content: String = "",
    ): ApiJournal

    @FormUrlEncoded
    @POST("reservations")
    suspend fun createReservation(
        @Field("userId") id: String,
        @Field("consultantId") consultantId: String,
        @Field("date") date: String,
        @Field("startTime") startTime: String,
        @Field("endTime") endTime: String,
    ): CreateReservationResponse

    @GET("reservations")
    suspend fun getReservations(): ReservationResponse

    @GET("reservations/{id}")
    suspend fun getReservationDetail(
        @Path("id") id: String,
    ): ApiReservation
}