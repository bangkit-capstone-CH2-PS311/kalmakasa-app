package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName

data class ConsentResponse(
    @field:SerializedName("url")
    val url: String,
)
