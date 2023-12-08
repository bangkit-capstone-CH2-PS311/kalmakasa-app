package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.response.toJournal
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.Journal
import com.kalmakasa.kalmakasa.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class JournalRepositoryImpl(
    private val apiService: ApiService,
) : JournalRepository {
    override suspend fun getJournals(): Flow<Resource<List<Journal>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getJournals()
        emit(Resource.Success(response.results.map { it.toJournal() }))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }

    override suspend fun getJournalDetail(id: String): Flow<Resource<Journal>> = flow {
        emit(Resource.Loading)
        val response = apiService.getJournalDetail(id)
        emit(Resource.Success(response.toJournal()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }
}