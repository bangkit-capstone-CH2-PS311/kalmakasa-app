package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Consultant
import kotlinx.coroutines.flow.Flow

interface ConsultantRepository {
    fun getDoctorDetailById(id: String): Flow<Resource<Consultant>>

    fun getListConsultant(): Flow<Resource<List<Consultant>>>
}