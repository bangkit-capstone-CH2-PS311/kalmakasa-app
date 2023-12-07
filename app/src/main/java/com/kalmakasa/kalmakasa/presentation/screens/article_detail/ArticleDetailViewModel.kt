package com.kalmakasa.kalmakasa.presentation.screens.article_detail

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
class ArticleDetailViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleDetailState())
    val uiState: StateFlow<ArticleDetailState> = _uiState.asStateFlow()

    fun getArticleDetail(id: String) {
        viewModelScope.launch {
            articleRepository.getArticleById(id).collect { article ->
                when (article) {
                    is Resource.Loading -> {
                        _uiState.value = ArticleDetailState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _uiState.value =
                            ArticleDetailState(
                                isLoading = false,
                                article = article.data,
                            )
                    }

                    else -> {
                        _uiState.value = ArticleDetailState(isError = true, isLoading = false)
                    }
                }
            }
        }
    }

}

data class ArticleDetailState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val article: Article? = null,
)