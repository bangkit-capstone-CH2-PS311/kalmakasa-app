package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.database.dao.MessageDao
import com.kalmakasa.kalmakasa.data.database.entity.MessageEntity
import com.kalmakasa.kalmakasa.data.database.entity.toMessage
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
        val responseTime = Calendar.getInstance().timeInMillis
        delay(5_000)
        val response = MessageEntity(
            msg = "this is bot response",
            isUser = false,
            timestamp = responseTime
        )
        messageDao.insert(response)
        emit(Resource.Success(true))
    }
}