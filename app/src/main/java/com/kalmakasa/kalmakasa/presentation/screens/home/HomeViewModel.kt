package com.kalmakasa.kalmakasa.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ListArticleState
import com.kalmakasa.kalmakasa.presentation.state.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
) : ViewModel() {

    val homeState = userRepository.getSession().map {
        if (it.isLogin) SessionState.LoggedIn(it) else SessionState.NotLoggedIn
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SessionState.Loading
    )

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
