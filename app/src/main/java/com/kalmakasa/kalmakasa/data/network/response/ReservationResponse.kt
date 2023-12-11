package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.domain.model.Reservation

data class ReservationResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("limit")
    val limit: Int,

    @field:SerializedName("totalPages")
    val totalPages: Int,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<ApiReservation>,
)

data class ApiReservation(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("consultantId")
    val consultantId: String,

    @field:SerializedName("startTime")
    val startTime: String,

    @field:SerializedName("endTime")
    val endTime: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("status")
    val status: String,
)

fun ApiReservation.toReservation() = Reservation(
    id = id,
    userId = userId,
    consultantId = consultantId,
    date = DateUtil.formatApiDate(date),
    time = "$startTime - $endTime",
    status = status
)
