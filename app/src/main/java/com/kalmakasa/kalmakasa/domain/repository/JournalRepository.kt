package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    suspend fun getJournals(): Flow<Resource<List<Journal>>>
    suspend fun getJournalDetail(id: String): Flow<Resource<Journal>>
}