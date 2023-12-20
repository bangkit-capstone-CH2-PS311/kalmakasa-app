package com.kalmakasa.kalmakasa.data.repository

import com.google.gson.Gson
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.response.toReservation
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.Patient
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.domain.model.ReservationReport
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
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

    override fun getReservationsPatients(): Flow<Resource<List<Patient>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getReservations()
        val reservations = response.results.sortedBy {
            DateUtil.apiToDate(it.date)
        }.map { it.toReservation().patient }.toSet().toList()
        emit(Resource.Success(reservations))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override fun getReservationByPatient(id: String): Flow<Resource<List<Reservation>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getReservations()
        val reservations = response.results
            .filter { it.patient.id == id }
            .sortedBy { DateUtil.apiToDate(it.date) }
            .map { it.toReservation() }
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

    override fun createReservation(
        userId: String,
        consultantId: String,
        profileId: String,
        date: String,
        startTime: String,
        endTime: String,
        note: String,
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        val response =
            apiService.createReservation(userId, consultantId, profileId, date, startTime, endTime)
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

    override fun createReservationLink(
        reservationId: String,
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        consultantId: String,
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        val response =
            apiService.generateLink(reservationId, date, startTime, endTime, userId, consultantId)
        emit(Resource.Success(response.url))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override fun getConsentLink(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        val response = apiService.getConsentLink()
        emit(Resource.Success(response.url))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> {
                emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
            }
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