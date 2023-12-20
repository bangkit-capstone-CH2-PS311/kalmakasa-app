package com.kalmakasa.kalmakasa.presentation.screens.article_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _articles = MutableStateFlow(emptyList<Article>())
    private val _isLoading = MutableStateFlow(false)
    private val _isError = MutableStateFlow(false)
    private val _query = MutableStateFlow("")

    val uiState: StateFlow<ListArticleState> = combine(
        _articles, _query, _isLoading, _isError
    ) { article, query, isLoading, isError ->
        val listArticle = if (_query.value.isNotBlank()) {
            article.filter {
                it.title.contains(
                    _query.value,
                    ignoreCase = true
                )
            }
        } else {
            article
        }
        ListArticleState(
            isLoading, isError, listArticle, query
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ListArticleState()
    )

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            articleRepository.getListArticles().collect { articles ->
                when (articles) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                        _articles.value = articles.data
                    }

                    else -> {
                        _isLoading.value = false
                        _isError.value = true
                    }
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _query.value = query
    }

}

data class ListArticleState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val listArticle: List<Article> = emptyList(),
    val query: String = "",
)