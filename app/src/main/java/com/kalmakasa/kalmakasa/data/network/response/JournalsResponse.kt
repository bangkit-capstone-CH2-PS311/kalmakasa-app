package com.kalmakasa.kalmakasa.data.network.response

import com.google.gson.annotations.SerializedName
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.Mood
import com.kalmakasa.kalmakasa.domain.model.Journal
import kotlin.random.Random

data class JournalsResponse(

    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("limit")
    val limit: Int,

    @field:SerializedName("totalPages")
    val totalPages: Int,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<ApiJournal>,
)

data class ApiJournal(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("content")
    val content: String,
)

fun ApiJournal.toJournal() = Journal(
    id = id,
    mood = Mood.intToMood(Random.nextInt(6)), // TODO : Belom ada di API 1 - 5
    date = DateUtil.formatApiDate(date),
    description = content,
)
