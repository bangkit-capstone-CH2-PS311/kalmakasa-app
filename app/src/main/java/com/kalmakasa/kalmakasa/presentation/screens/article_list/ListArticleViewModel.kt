package com.kalmakasa.kalmakasa.presentation.screens.article_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListArticleState())
    val uiState: StateFlow<ListArticleState> = _uiState.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        viewModelScope.launch {
            articleRepository.getListArticles().collect { articles ->
                when (articles) {
                    is Resource.Loading -> {
                        _uiState.value = ListArticleState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value = ListArticleState(listArticle = articles.data)
                    }

                    else -> {
                        _uiState.value = ListArticleState(isError = true)
                    }
                }
            }
        }
    }

}

data class ListArticleState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val listArticle: List<Article> = emptyList(),
)