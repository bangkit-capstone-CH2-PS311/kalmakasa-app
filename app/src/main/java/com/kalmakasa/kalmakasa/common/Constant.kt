package com.kalmakasa.kalmakasa.common

import android.content.Context
import android.content.Intent
import android.net.Uri

const val BASE_URL = "http://10.0.2.2:8080/v1/"
const val ML_URL = "https://kalmakasa-ml-hvdw7bfzla-et.a.run.app/"

val MOODS = listOf(
    Mood.VERY_SAD,
    Mood.SAD,
    Mood.FLAT,
    Mood.HAPPY,
    Mood.VERY_HAPPY,
)

val EXPERTISES = listOf(
    Tag.STRESS.text,
    Tag.ANXIETY.text,
    Tag.DEPRESSION.text,
    Tag.CAREER.text,
    Tag.FAMILY.text,
    Tag.RELATIONSHIP.text,
)

val INCLUDED_LEVEL = listOf(
    HealthTestLevel.Severe,
    HealthTestLevel.ExtremelySevere,
    HealthTestLevel.Moderate
)

fun linkIntent(link: String, context: Context) {
    val url = Uri.parse(link)
    val intent = Intent(Intent.ACTION_VIEW, url)
    context.startActivity(intent)
}

