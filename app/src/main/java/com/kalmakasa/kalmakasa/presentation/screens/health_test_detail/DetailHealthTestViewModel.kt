package com.kalmakasa.kalmakasa.presentation.screens.health_test_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.HealthTestType
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.domain.model.HealthTestResult
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import com.kalmakasa.kalmakasa.domain.repository.HealthTestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailHealthTestViewModel @Inject constructor(
    private val healthTestRepository: HealthTestRepository,
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<Resource<HealthTestResult>> =
        MutableStateFlow(Resource.Loading)

    val uiState = _uiState.asStateFlow()
    private val _article: MutableStateFlow<Resource<List<Article>>> =
        MutableStateFlow(Resource.Loading)
    val article = _article.asStateFlow()

    fun getHealthTestDetail(id: String) {
        viewModelScope.launch {
            healthTestRepository.getHealthTestDetail(id).collect { result ->
                _uiState.value = result
                if (result is Resource.Success) {
                    articleRepository.getListArticles().collect { articles ->
                        if (articles is Resource.Success) {
                            val tags = HealthTestType.getHealthTestActiveTag(
                                result.data.depression,
                                result.data.anxiety,
                                result.data.stress
                            )
                            val filtered = articles.data.filter { article ->
                                article.tags.map { it.text }.any { it in tags }
                            }
                            if (filtered.isEmpty()) {
                                _article.value = articles
                            } else {
                                _article.value = Resource.Success(filtered)
                            }
                        } else {
                            _article.value = articles
                        }
                    }
                }
            }
        }
    }
}