package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Doctor
import kotlinx.coroutines.flow.Flow

interface DoctorRepository {
    fun getDoctorDetailById(id: String): Flow<Resource<Doctor>>
}