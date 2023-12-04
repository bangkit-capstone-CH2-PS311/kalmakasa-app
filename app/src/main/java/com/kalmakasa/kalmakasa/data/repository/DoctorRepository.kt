package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.data.model.Doctor
import kotlinx.coroutines.flow.Flow

interface DoctorRepository {
    fun getDoctorList(): Flow<List<Doctor>>
}