package com.kalmakasa.kalmakasa.data.repository

import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.FakeArticleDataSource
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class ArticleRepositoryImpl : ArticleRepository {
    override fun getListArticles(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading)
        delay(500)
        emit(Resource.Success(FakeArticleDataSource.articles))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }

    override fun getArticleById(id: String): Flow<Resource<Article>> = flow {
        delay(500)
        val article = FakeArticleDataSource.articles.find { it.id == id }
        article?.let {
            emit(Resource.Success(it))
        } ?: emit(Resource.Error("Article Not Found"))
    }.catch {
        when (it) {
            is HttpException -> emit(Resource.Error(it.localizedMessage ?: "Unknown Error"))
            is IOException -> emit(Resource.Error(it.localizedMessage ?: "No Internet"))
        }
    }
}

