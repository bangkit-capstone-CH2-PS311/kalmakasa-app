package com.kalmakasa.kalmakasa.data.network.retrofit

import com.kalmakasa.kalmakasa.data.network.response.PredictResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MachineLearningService {
    @Headers("Content-Type: application/json")
    @POST("predict")
    suspend fun getPrediction(
        @Body requestBody: RequestBody,
    ): PredictResponse
}