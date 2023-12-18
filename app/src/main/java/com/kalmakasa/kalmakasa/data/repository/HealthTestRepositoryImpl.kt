package com.kalmakasa.kalmakasa.data.repository

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kalmakasa.kalmakasa.common.HealthTestType
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.database.dao.HealthTestDao
import com.kalmakasa.kalmakasa.data.database.entity.toHealthTestResult
import com.kalmakasa.kalmakasa.data.network.response.toEntity
import com.kalmakasa.kalmakasa.data.network.response.toHealthTestResult
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import com.kalmakasa.kalmakasa.domain.repository.HealthTestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HealthTestRepositoryImpl @Inject constructor(
    private val healthTestDao: HealthTestDao,
    private val apiService: ApiService,
) : HealthTestRepository {

    override suspend fun getHealthTests(): Flow<Resource<List<HealthTestResult>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getHealthTest()
        if (response.results.isNotEmpty()) {
            healthTestDao.deleteAll()
            healthTestDao.insert(response.results.first().toEntity())
        }
        emit(Resource.Success(response.results.map { it.toHealthTestResult() }))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun getHealthTestDetail(id: String): Flow<Resource<HealthTestResult>> = flow {
        emit(Resource.Loading)
        delay(2000)
        val response = apiService.getHealthTestDetail(id)
        emit(Resource.Success(response.toHealthTestResult()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun createHealthTest(
        userId: String,
        questionScore: List<Int>,
    ): Flow<Resource<HealthTestResult>> = flow {
        emit(Resource.Loading)
        delay(2000)
        val response = apiService.createHealthTestResult(createRequestBody(userId, questionScore))
        healthTestDao.deleteAll()
        healthTestDao.insert(response.toEntity())
        emit(Resource.Success(response.toHealthTestResult()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun getHealthTestTag(): Flow<List<String>> {
        val data = healthTestDao.getHealthTestResults().map {
            if (it.isNotEmpty()) {
                val result = it.first().toHealthTestResult()
                HealthTestType.getHealthTestActiveTag(
                    result.depression,
                    result.anxiety,
                    result.stress
                )
            } else {
                emptyList()
            }
        }
        return data
    }

    private fun createRequestBody(userId: String, questionScore: List<Int>): RequestBody {
        val gson = Gson()
        val data = HealthTestRequest(userId, questionScore)
        val json = gson.toJson(data)
        val mediaType = "application/json".toMediaTypeOrNull()
        return json.toRequestBody(mediaType)
    }
}

data class HealthTestRequest(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("questionScore")
    val questionScore: List<Int>,

    )