package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Patient
import com.kalmakasa.kalmakasa.domain.model.Reservation
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    suspend fun getReservations(): Flow<Resource<List<Reservation>>>

    fun getReservationsPatients(): Flow<Resource<List<Patient>>>
    suspend fun getReservationDetail(id: String): Flow<Resource<Reservation>>

    fun getReservationByPatient(id: String): Flow<Resource<List<Reservation>>>

    fun createReservation(
        userId: String,
        consultantId: String,
        profileId: String,
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

    fun createReservationLink(
        reservationId: String,
        date: String,
        startTime: String,
        endTime: String,
        userId: String,
        consultantId: String,
    ): Flow<Resource<String>>

    fun getConsentLink(): Flow<Resource<String>>

}