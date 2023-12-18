package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getMessages(): Flow<List<Message>>
    suspend fun sendMessage(message: String): Flow<Resource<Boolean>>
}