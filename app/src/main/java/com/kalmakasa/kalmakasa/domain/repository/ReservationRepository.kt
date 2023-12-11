package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.response.ApiReservation
import com.kalmakasa.kalmakasa.domain.model.Reservation
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    suspend fun getReservations(): Flow<Resource<List<Reservation>>>
    suspend fun createReservation(): Flow<Resource<ApiReservation>>
}