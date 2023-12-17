package com.kalmakasa.kalmakasa.domain.model

import com.kalmakasa.kalmakasa.common.Tag

data class Article(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val tags: List<Tag> = emptyList(),
)