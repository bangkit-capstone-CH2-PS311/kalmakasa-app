package com.kalmakasa.kalmakasa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kalmakasa.kalmakasa.data.database.entity.HealthTestResultEntity

@Database(entities = [HealthTestResultEntity::class], version = 1)
abstract class KalmakasaRoomDatabase : RoomDatabase() {
    abstract fun healthTestDao(): HealthTestDao
}