package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.response.toConsultant
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.Consultant
import com.kalmakasa.kalmakasa.domain.repository.ConsultantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ConsultantRepositoryImpl(
    private val api: ApiService,
) : ConsultantRepository {

    override fun getListConsultant(): Flow<Resource<List<Consultant>>> = flow {
        emit(Resource.Loading)
        val response = api.getListConsultants()
        emit(Resource.Success(response.results.map { it.toConsultant() }))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override fun getDoctorDetailById(id: String): Flow<Resource<Consultant>> = flow {
        emit(Resource.Loading)
        val response = api.getConsultantDetail(id)
        emit(Resource.Success(response.toConsultant()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }
}
