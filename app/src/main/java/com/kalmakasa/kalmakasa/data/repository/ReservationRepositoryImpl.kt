package com.kalmakasa.kalmakasa.data.repository

import com.google.gson.Gson
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.response.toReservation
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.domain.model.ReservationReport
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException

class ReservationRepositoryImpl(
    private val apiService: ApiService,
) : ReservationRepository {
    override suspend fun getReservations(): Flow<Resource<List<Reservation>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getReservations()
        val reservations = response.results.sortedBy {
            DateUtil.apiToDate(it.date)
        }.map { it.toReservation() }
        emit(Resource.Success(reservations))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun getReservationDetail(id: String): Flow<Resource<Reservation>> = flow {
        emit(Resource.Loading)
        val response = apiService.getReservationDetail(id)
        emit(Resource.Success(response.toReservation()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun createReservation(
        userId: String,
        consultantId: String,
        date: String,
        startTime: String,
        endTime: String,
        note: String,
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        val response = apiService.createReservation(userId, consultantId, date, startTime, endTime)
        emit(Resource.Success(response.msg))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun createReservationReport(
        id: String,
        commonIssues: String,
        psychologicalDynamics: String,
        triggers: String,
        recommendation: String,
    ): Flow<Resource<Reservation>> = flow {
        emit(Resource.Loading)
        delay(5000)
        val requestBody =
            createRequestBody(commonIssues, psychologicalDynamics, triggers, recommendation)

        val response = apiService.createReservationReport(id, requestBody)
        emit(Resource.Success(response.toReservation()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    private fun createRequestBody(
        commonIssues: String,
        psychologicalDynamics: String,
        triggers: String,
        recommendation: String,
    ): RequestBody {
        val gson = Gson()
        val data = ReservationReport(commonIssues, psychologicalDynamics, recommendation, triggers)
        val json = gson.toJson(data)
        val mediaType = "application/json".toMediaTypeOrNull()
        return json.toRequestBody(mediaType)
    }

}