package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("data")
	val value: String,

	@field:SerializedName("status")
	val predictStatus: PredictStatus,
)

data class PredictStatus(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String,
)
