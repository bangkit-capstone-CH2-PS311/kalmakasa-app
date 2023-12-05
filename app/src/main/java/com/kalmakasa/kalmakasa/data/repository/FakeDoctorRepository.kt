package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.model.toDoctor
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.Doctor
import com.kalmakasa.kalmakasa.domain.repository.DoctorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class FakeDoctorRepository(
    private val api: ApiService,
) : DoctorRepository {
    override fun getDoctorDetailById(id: String): Flow<Resource<Doctor>> = flow {
        try {
            emit(Resource.Loading)
            val response = api.getDoctorById(id)
            if (response.error) {
                emit(Resource.Error(response.message))
            } else {
                response.doctor?.let {
                    emit(Resource.Success(it.toDoctor()))
                } ?: emit(Resource.Error("Not Found"))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Couldn't connect to the server, please check your connection"
                )
            )
        } catch (e: IOException) {
            // IO Exception
            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Error occurred, please try again"
                )
            )
        }
    }

    override fun getListDoctor(): Flow<Resource<List<Doctor>>> = flow {
        try {
            emit(Resource.Loading)
            val response = api.getListDoctor()
            if (response.error) {
                emit(Resource.Error(response.message))
            } else {
                emit(Resource.Success(response.listDoctor.map { it.toDoctor() }))
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Couldn't connect to the server, please check your connection"
                )
            )
        } catch (e: IOException) {
            // IO Exception
            emit(
                Resource.Error(
                    e.localizedMessage
                        ?: "Error occurred, please try again"
                )
            )
        }
    }
}