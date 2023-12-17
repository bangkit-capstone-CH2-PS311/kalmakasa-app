package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.HealthTestType
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.database.HealthTestDao
import com.kalmakasa.kalmakasa.data.database.entity.HealthTestResultEntity
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import com.kalmakasa.kalmakasa.domain.repository.HealthTestRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

class HealthTestRepositoryImpl @Inject constructor(
    private val healthTestDao: HealthTestDao,
) : HealthTestRepository {

    private val healthTestResults = listOf(
        HealthTestResult(
            "id-1",
            HealthTestType.Depression(Random.nextInt(21)),
            HealthTestType.Anxiety(Random.nextInt(21)),
            HealthTestType.Stress(Random.nextInt(21)),
            "29 Des 2022, 19:00"
        ),
        HealthTestResult(
            "id-2",
            HealthTestType.Depression(Random.nextInt(21)),
            HealthTestType.Anxiety(Random.nextInt(21)),
            HealthTestType.Stress(Random.nextInt(21)),
            "29 Des 2022, 19:00"
        ),
        HealthTestResult(
            "id-3",
            HealthTestType.Depression(Random.nextInt(21)),
            HealthTestType.Anxiety(Random.nextInt(21)),
            HealthTestType.Stress(Random.nextInt(21)),
            "29 Des 2022, 19:00"
        ),
    )

    override suspend fun getHealthTests(): Flow<Resource<List<HealthTestResult>>> = flow {
        emit(Resource.Loading)
        delay(2000)
        emit(Resource.Success(healthTestResults))
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
        val healthTestResult = healthTestResults.find { it.id == id }
        if (healthTestResult != null) {
            emit(Resource.Success(healthTestResult))
        } else {
            emit(Resource.Error("Not Found"))
        }
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }

    override suspend fun createHealthTest(): Flow<Resource<HealthTestResult>> = flow {
        emit(Resource.Loading)
        delay(2000)
        val healthTest = HealthTestResult(
            "id-3",
            HealthTestType.Depression(Random.nextInt(21)),
            HealthTestType.Anxiety(Random.nextInt(21)),
            HealthTestType.Stress(Random.nextInt(21)),
            "29 Des 2022, 19:00"
        )
        healthTestDao.insert(
            HealthTestResultEntity(
                depression = 2,
                anxiety = 3,
                stress = 5,
                date = "29 Des 2022, 19:00"
            )
        )
        emit(Resource.Success(healthTest))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }
}