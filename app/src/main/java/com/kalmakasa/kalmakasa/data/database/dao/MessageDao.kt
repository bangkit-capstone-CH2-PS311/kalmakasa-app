package com.kalmakasa.kalmakasa.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kalmakasa.kalmakasa.data.database.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert
    suspend fun insert(message: MessageEntity)

    @Insert
    suspend fun insert(message: List<MessageEntity>)

    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<MessageEntity>>
}