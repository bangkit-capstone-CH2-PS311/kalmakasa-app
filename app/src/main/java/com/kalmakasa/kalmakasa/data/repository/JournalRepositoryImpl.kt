package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.Model
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.common.createPredictRequestBody
import com.kalmakasa.kalmakasa.common.predictToSliderValue
import com.kalmakasa.kalmakasa.common.predictToTag
import com.kalmakasa.kalmakasa.data.network.response.toJournal
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.data.network.retrofit.MachineLearningService
import com.kalmakasa.kalmakasa.domain.model.Journal
import com.kalmakasa.kalmakasa.domain.model.JournalPrediction
import com.kalmakasa.kalmakasa.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class JournalRepositoryImpl(
    private val apiService: ApiService,
    private val mlService: MachineLearningService,
) : JournalRepository {
    override suspend fun getJournals(): Flow<Resource<List<Journal>>> = flow {
        emit(Resource.Loading)
        val response = apiService.getJournals()
        val journals = response.results.sortedByDescending {
            DateUtil.apiToDate(it.date)
        }.map { it.toJournal() }
        emit(Resource.Success(journals))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }

    override suspend fun getTodayJournal(): Flow<Resource<Journal>> = flow {
        emit(Resource.Loading)
        val response = apiService.getJournals()
        val journal = response.results.filter {
            val date = DateUtil.apiToDate(it.date)
            date?.let {
                DateUtil.isDateToday(date)
            } ?: false
        }

        emit(Resource.Success(journal.first().toJournal()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }

    override suspend fun createJournal(
        id: String,
        date: String,
        content: String,
        emoticonScale: Int,
    ): Flow<Resource<Journal>> = flow {
        emit(Resource.Loading)
        val response = apiService.createJournal(id, date, content, emoticonScale)
        emit(Resource.Success(response.toJournal()))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))

        }
    }

    override fun predictJournalMood(journal: String): Flow<Resource<JournalPrediction>> = flow {
        emit(Resource.Loading)
        val requestMood = createPredictRequestBody(journal, Model.Mood.modelName)
        val requestJournal = createPredictRequestBody(journal, Model.Journal.modelName)
        val responseMood = mlService.getPrediction(requestMood)
        val responseJournal = mlService.getPrediction(requestJournal)

        val prediction = JournalPrediction(
            responseMood.value,
            predictToSliderValue(responseMood.value),
            predictToTag(responseJournal.value),
        )

        emit(Resource.Success(prediction))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }
}
