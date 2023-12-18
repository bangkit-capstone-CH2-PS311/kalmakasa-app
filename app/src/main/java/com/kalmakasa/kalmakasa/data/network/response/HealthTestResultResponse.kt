package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.HealthTestType
import com.kalmakasa.kalmakasa.data.database.entity.HealthTestResultEntity
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import java.util.Calendar

data class HealthTestResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("limit")
    val limit: Int,

    @field:SerializedName("totalPages")
    val totalPages: Int,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<ApiHealthTestResult>,
)

data class ApiHealthTestResult(

    @field:SerializedName("anxiety")
    val anxiety: Int,

    @field:SerializedName("stress")
    val stress: Int,

    @field:SerializedName("questionScore")
    val questionScore: List<Int?>,

    @field:SerializedName("depression")
    val depression: Int,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("userId")
    val userId: String,
)

fun ApiHealthTestResult.toHealthTestResult() = HealthTestResult(
    id = id,
    depression = HealthTestType.Depression(depression),
    anxiety = HealthTestType.Anxiety(anxiety),
    stress = HealthTestType.Stress(stress),
    date = DateUtil.millisToDate(Calendar.getInstance().timeInMillis),

    )

fun ApiHealthTestResult.toEntity() = HealthTestResultEntity(
    depression = depression,
    anxiety = anxiety,
    stress = stress,
    date = DateUtil.millisToDate(Calendar.getInstance().timeInMillis),
)


