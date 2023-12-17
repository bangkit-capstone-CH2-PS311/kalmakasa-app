package com.kalmakasa.kalmakasa.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("health_test")
data class HealthTestResultEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "depression")
    val depression: Int,

    @ColumnInfo(name = "anxiety")
    val anxiety: Int,

    @ColumnInfo(name = "stress")
    val stress: Int,

    @ColumnInfo(name = "date")
    val date: String,
)