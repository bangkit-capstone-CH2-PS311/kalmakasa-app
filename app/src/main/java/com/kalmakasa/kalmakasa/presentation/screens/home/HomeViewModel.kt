package com.kalmakasa.kalmakasa.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Journal
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import com.kalmakasa.kalmakasa.domain.repository.JournalRepository
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import com.kalmakasa.kalmakasa.presentation.screens.article_list.ListArticleState
import com.kalmakasa.kalmakasa.presentation.state.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val journalRepository: JournalRepository,
) : ViewModel() {

    private val sessionState = userRepository.getSession().map {
        if (it.isLogin) SessionState.LoggedIn(it) else SessionState.NotLoggedIn
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SessionState.Loading
    )

    private val _journalState: MutableStateFlow<Journal?> = MutableStateFlow(null)

    private val _articleState = MutableStateFlow(ListArticleState())

    val uiState: StateFlow<HomeState> = combine(
        sessionState,
        _journalState,
        _articleState
    ) { session, journal, article ->
        HomeState(session, article, journal)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeState()
    )

    init {
        getArticles()
        getTodayJournal()
    }

    private fun getArticles() {
        viewModelScope.launch {
            articleRepository.getListArticles().collect { articles ->
                when (articles) {
                    is Resource.Loading -> {
                        _articleState.value = ListArticleState(isLoading = true)
                    }

                    is Resource.Success -> {
                        _articleState.value = ListArticleState(listArticle = articles.data)
                    }

                    else -> {
                        _articleState.value = ListArticleState(isError = true)
                    }
                }
            }
        }
    }

    private fun getTodayJournal() {
        viewModelScope.launch {
            journalRepository.getTodayJournal().collect { journals ->
                when (journals) {
                    is Resource.Success -> {
                        _journalState.value = journals.data
                    }

                    else -> {
                        _journalState.value = null
                    }
                }
            }
        }
    }
}

data class HomeState(
    val sessionState: SessionState = SessionState.Loading,
    val articleState: ListArticleState = ListArticleState(isLoading = true),
    val journal: Journal? = null,
)
