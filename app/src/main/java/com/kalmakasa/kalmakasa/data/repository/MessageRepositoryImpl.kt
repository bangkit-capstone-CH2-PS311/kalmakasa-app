package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.database.dao.MessageDao
import com.kalmakasa.kalmakasa.data.database.entity.MessageEntity
import com.kalmakasa.kalmakasa.data.database.entity.toMessage
import com.kalmakasa.kalmakasa.data.network.retrofit.ApiService
import com.kalmakasa.kalmakasa.domain.model.Message
import com.kalmakasa.kalmakasa.domain.repository.MessageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val apiService: ApiService,
) : MessageRepository {
    override fun getMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages().map { listMessage ->
            listMessage.sortedBy { it.timestamp }.map { it.toMessage() }
        }
    }

    override suspend fun sendMessage(message: String): Flow<Resource<Boolean>> = flow {
        val sendTime = Calendar.getInstance().timeInMillis
        messageDao.insert(
            MessageEntity(
                msg = message,
                isUser = true,
                timestamp = sendTime
            )
        )
        // TODO : FETCH API TO GET KALMBOT RESPONSE
        emit(Resource.Loading)
//        val response = apiService.getChatBotResponse(message)
        val responseTime = Calendar.getInstance().timeInMillis
        val response = MessageEntity(
            msg = "This is Response",
            isUser = false,
            timestamp = responseTime
        )
        delay(5_000)
        messageDao.insert(response)
        emit(Resource.Success(true))
    }
}