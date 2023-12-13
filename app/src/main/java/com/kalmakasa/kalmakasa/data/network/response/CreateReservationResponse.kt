package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName

data class CreateReservationResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("reservation")
	val reservation: ApiCreateReservation,
)

data class ApiCreateReservation(

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
