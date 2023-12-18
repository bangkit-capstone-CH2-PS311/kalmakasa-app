package com.kalmakasa.kalmakasa.common

const val BASE_URL = "http://10.0.2.2:8080/v1/"

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
