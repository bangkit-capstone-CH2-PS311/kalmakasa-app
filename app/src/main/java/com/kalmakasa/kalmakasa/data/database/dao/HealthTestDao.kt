package com.kalmakasa.kalmakasa.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.kalmakasa.kalmakasa.data.database.entity.HealthTestResultEntity

@Dao
interface HealthTestDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(healthTest: HealthTestResultEntity)
}