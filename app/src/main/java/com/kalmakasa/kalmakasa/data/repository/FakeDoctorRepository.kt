package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Doctor
import com.kalmakasa.kalmakasa.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDoctorRepository() : DoctorRepository {
    override fun getDoctorDetailById(id: String): Flow<Resource<Doctor>> = flow {
        emit(Resource.Loading)

    }
}