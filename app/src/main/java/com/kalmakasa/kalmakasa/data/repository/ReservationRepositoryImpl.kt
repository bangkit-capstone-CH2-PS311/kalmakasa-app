package com.kalmakasa.kalmakasa.data.repository

import android.util.Log
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.response.toReservation
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ReservationRepositoryImpl(
    private val apiService: ApiService,
) : ReservationRepository {
    override suspend fun getReservations(): Flow<Resource<List<Reservation>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getReservations()
        emit(Resource.Success(response.results.map { it.toReservation() }))
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
            else -> {
                Log.d("Repo", "createReservation: ${it.localizedMessage}")
                emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
            }
        }
    }

}