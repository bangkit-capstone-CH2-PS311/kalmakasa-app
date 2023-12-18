package com.kalmakasa.kalmakasa.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.HealthTestType
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import java.util.Calendar

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

fun HealthTestResultEntity.toHealthTestResult() = HealthTestResult(
    id = id.toString(),
    depression = HealthTestType.Depression(depression),
    anxiety = HealthTestType.Anxiety(anxiety),
    stress = HealthTestType.Stress(stress),
    date = DateUtil.millisToDate(Calendar.getInstance().timeInMillis),
)
