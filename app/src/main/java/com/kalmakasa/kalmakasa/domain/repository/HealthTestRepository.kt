package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import kotlinx.coroutines.flow.Flow

interface HealthTestRepository {

    suspend fun getHealthTests(): Flow<Resource<List<HealthTestResult>>>
    suspend fun getHealthTestDetail(id: String): Flow<Resource<HealthTestResult>>

    suspend fun createHealthTest(): Flow<Resource<HealthTestResult>>
}