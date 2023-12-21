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
    Mood("twitter"),
    Chatbot("chatbot"),
    Journal("reddit")
}

fun predictToSliderValue(predict: String): Float {
    return when (predict) {
        "anger" -> 0f
        "fear" -> 1f
        "sadness" -> 1f
        "love" -> 4f
        "happy" -> 3f
        "joy" -> 3f
        else -> 2f
    }
}

fun predictToTag(predict: String): Tag? {
    return when (predict) {
        "Stress" -> Tag.STRESS
        "Depression" -> Tag.DEPRESSION
        "Anxiety" -> Tag.ANXIETY
        "Bipolar" -> Tag.BIPOLAR
        "Personality disorder" -> Tag.PERSONALITY
        else -> null
    }
}

data class PredictRequest(
    @field:SerializedName("text")
    val text: String,

    @field:SerializedName("model_type")
    val modelType: String,
)
