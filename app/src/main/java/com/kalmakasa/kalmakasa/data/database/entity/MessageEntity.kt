package com.kalmakasa.kalmakasa.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.domain.model.Message

@Entity("messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "msg")
    val msg: String,

    @ColumnInfo(name = "isUser")
    val isUser: Boolean,

    @ColumnInfo(name = "date")
    val timestamp: Long = 0,
)

fun MessageEntity.toMessage() = Message(
    msg, isUser, DateUtil.millisToDate(timestamp)
)