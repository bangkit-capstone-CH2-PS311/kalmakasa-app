package com.kalmakasa.kalmakasa.domain.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticleById(id: String): Flow<Resource<Article>>

    fun getListArticles(): Flow<Resource<List<Article>>>
}