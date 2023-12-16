package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.ReservationStatus
import com.kalmakasa.kalmakasa.domain.model.Reservation
import com.kalmakasa.kalmakasa.domain.model.ReservationReport

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
    val consultant: ApiConsultant,

    @field:SerializedName("startTime")
    val startTime: String,

    @field:SerializedName("endTime")
    val endTime: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("userId")
    val patient: ApiUser,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("notes")
    val notes: String? = null,

    @field:SerializedName("commonIssues")
    val commonIssues: String? = null,

    @field:SerializedName("psychologicalDynamics")
    val psychologicalDynamics: String? = null,

    @field:SerializedName("recommendation")
    val recommendation: String? = null,

    @field:SerializedName("triggers")
    val triggers: String? = null,
)

fun ApiReservation.toReservation(): Reservation {
    val report = if (
        this.recommendation != null &&
        this.commonIssues != null &&
        this.psychologicalDynamics != null &&
        this.triggers != null
    ) {
        ReservationReport(
            commonIssues, psychologicalDynamics, recommendation, triggers
        )
    } else null
    return Reservation(
        id = id,
        date = DateUtil.formatApiDate(date),
        time = "$startTime - $endTime",
        patient = patient.toPatient(),
        consultant = consultant.toConsultant(),
        status = ReservationStatus.getStatus(status),
        notes = notes ?: "there is no note",
        report = report,
    )
}
