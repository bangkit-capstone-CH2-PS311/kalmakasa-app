package com.kalmakasa.kalmakasa.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kalmakasa.kalmakasa.data.database.entity.HealthTestResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthTestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(healthTest: HealthTestResultEntity)

    @Query("DELETE FROM health_test")
    suspend fun deleteAll()

    @Query("SELECT * FROM health_test")
    fun getHealthTestResults(): Flow<List<HealthTestResultEntity>>
}