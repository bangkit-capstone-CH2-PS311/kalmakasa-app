package com.kalmakasa.kalmakasa.common

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun createPredictRequestBody(text: String, model: String): RequestBody {
    val gson = Gson()
    val data = PredictRequest(text, model)
    return gson.toJson(data).toRequestBody()
}

enum class Model(val modelName: String) {
    Journal("twitter"),
    Chatbot("chatbot"),
}

fun translatePredictToSliderValue(predict: String): Float {
    return when (predict) {
        "anger" -> 0f
        "fear" -> 1f
        "sadness" -> 1f
        "love" -> 4f
        "happy" -> 3f
        else -> 2f
    }
}

data class PredictRequest(
    @field:SerializedName("text")
    val text: String,

    @field:SerializedName("model_type")
    val modelType: String,
)
