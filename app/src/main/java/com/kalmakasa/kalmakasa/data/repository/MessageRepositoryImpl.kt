package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Model
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.common.createPredictRequestBody
import com.kalmakasa.kalmakasa.data.database.dao.MessageDao
import com.kalmakasa.kalmakasa.data.database.entity.MessageEntity
import com.kalmakasa.kalmakasa.data.database.entity.toMessage
import com.kalmakasa.kalmakasa.data.network.retrofit.MachineLearningService
import com.kalmakasa.kalmakasa.domain.model.Message
import com.kalmakasa.kalmakasa.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val mlService: MachineLearningService,
) : MessageRepository {
    override fun getMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages().map { listMessage ->
            listMessage.sortedByDescending { it.timestamp }.map { it.toMessage() }
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
        emit(Resource.Loading)
        val requestBody = createPredictRequestBody(message, Model.Chatbot.modelName)
        val response = mlService.getPrediction(requestBody)

        val responseTime = Calendar.getInstance().timeInMillis
        val botMessage = MessageEntity(
            msg = response.value,
            isUser = false,
            timestamp = responseTime
        )
        messageDao.insert(botMessage)
        emit(Resource.Success(true))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
            else -> emit(Resource.Error(it.localizedMessage ?: "Unknown error occurred"))
        }
    }
}