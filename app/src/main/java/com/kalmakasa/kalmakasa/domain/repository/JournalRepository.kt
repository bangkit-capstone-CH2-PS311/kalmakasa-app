package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Journal
import com.kalmakasa.kalmakasa.domain.model.JournalPrediction
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    suspend fun getJournals(): Flow<Resource<List<Journal>>>

    suspend fun createJournal(
        id: String,
        date: String,
        content: String,
        emoticonScale: Int,
    ): Flow<Resource<Journal>>

    fun predictJournalMood(journal: String): Flow<Resource<JournalPrediction>>

    suspend fun getTodayJournal(): Flow<Resource<Journal>>
}