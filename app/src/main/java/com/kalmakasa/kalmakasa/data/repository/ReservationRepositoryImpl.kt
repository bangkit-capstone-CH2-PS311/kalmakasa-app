package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.response.ApiReservation
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
        }
    }

    override suspend fun createReservation(): Flow<Resource<ApiReservation>> {
        TODO("API Belom bisa")
    }

}