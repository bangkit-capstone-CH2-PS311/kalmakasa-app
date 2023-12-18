package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    suspend fun getJournals(): Flow<Resource<List<Journal>>>

    suspend fun createJournal(
        id: String,
        date: String,
        content: String,
        emoticonScale: Int,
    ): Flow<Resource<Journal>>

    suspend fun getTodayJournal(): Flow<Resource<Journal>>
}