package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Reservation
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    suspend fun getReservations(): Flow<Resource<List<Reservation>>>
    suspend fun getReservationDetail(id: String): Flow<Resource<Reservation>>
    suspend fun createReservation(
        userId: String,
        consultantId: String,
        date: String,
        startTime: String,
        endTime: String,
        note: String,
    ): Flow<Resource<String>>

    suspend fun createReservationReport(
        id: String,
        commonIssues: String,
        psychologicalDynamics: String,
        triggers: String,
        recommendation: String,
    ): Flow<Resource<Reservation>>
}