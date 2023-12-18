package com.kalmakasa.kalmakasa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalmakasa.kalmakasa.data.database.dao.HealthTestDao
import com.kalmakasa.kalmakasa.data.database.dao.MessageDao
import com.kalmakasa.kalmakasa.data.database.entity.HealthTestResultEntity
import com.kalmakasa.kalmakasa.data.database.entity.MessageEntity

@Database(entities = [HealthTestResultEntity::class, MessageEntity::class], version = 2)
abstract class KalmakasaRoomDatabase : RoomDatabase() {
    abstract fun healthTestDao(): HealthTestDao
    abstract fun messageDao(): MessageDao
}