package com.kalmakasa.kalmakasa.presentation.screens.journal_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.DateUtil
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.model.Article
import com.kalmakasa.kalmakasa.domain.repository.ArticleRepository
import com.kalmakasa.kalmakasa.domain.repository.JournalRepository
import com.kalmakasa.kalmakasa.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddJournalViewModel @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val journalRepository: JournalRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _currentStepIndex = MutableStateFlow(0)
    private val _sliderValue = MutableStateFlow(2f)
    private val _journalValue = MutableStateFlow("")
    private val _recommendationContent = MutableStateFlow(emptyList<Article>())
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<AddJournalState> =
        combine(
            _currentStepIndex,
            _sliderValue,
            _journalValue,
            _recommendationContent,
            _isLoading,
        ) { currentIndex, slider, journal, articles, isLoading ->
            AddJournalState(
                currentIndex = currentIndex,
                currentStep = journalStep[currentIndex],
                progress = (currentIndex + 1).toFloat() / journalStep.size,
                sliderValue = slider,
                journalValue = journal,
                recommendationContent = articles,
                isLoading = isLoading,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AddJournalState()
        )

    fun nextStep() {
        viewModelScope.launch {
            when (journalStep[_currentStepIndex.value]) {
                JournalStep.Journal -> {
                    // Send and get emotion
                    _isLoading.value = true
                    delay(2000)
                    _isLoading.value = false
                    _currentStepIndex.value++
                }

                JournalStep.Emotion -> {
                    // Submit and get Article
                    val user = userRepository.getSession().first()
                    journalRepository.createJournal(
                        user.id,
                        DateUtil.getCurrentDateInISO(),
                        _journalValue.value,
                        (_sliderValue.value.toInt() + 1)
                    ).collect {
                        when (it) {
                            Resource.Loading -> {
                                _isLoading.value = true
                            }

                            is Resource.Success -> {
                                _currentStepIndex.value++
                                loadRecommendation()
                            }

                            else -> {
                                _isLoading.value = false
                            }
                        }
                    }

                }

                else -> {
                    _isLoading.value = false
                }
            }
        }

    }

    fun prevStep() {
        _currentStepIndex.value--
    }

    fun onSliderChange(value: Float) {
        _sliderValue.value = value
    }

    fun onJournalChange(value: String) {
        _journalValue.value = value
    }

    companion object {
        private val journalStep = listOf(
            JournalStep.Journal,
            JournalStep.Emotion,
            JournalStep.Recommendation,
        )
    }

    private fun loadRecommendation() {
        viewModelScope.launch {
            articleRepository.getListArticles().collect { articles ->
                when (articles) {
                    Resource.Loading -> {
                        _isLoading.value = true
                    }

                    is Resource.Success -> {
                        _isLoading.value = false
                        _recommendationContent.value = articles.data.take(4)
                    }

                    else -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

}

data class AddJournalState(
    val currentIndex: Int = 0,
    val progress: Float = 0f,
    val recommendationContent: List<Article> = emptyList(),
    val currentStep: JournalStep = JournalStep.Journal,
    val sliderValue: Float = 2f,
    val journalValue: String = "",
    val isLoading: Boolean = false,
)

enum class JournalStep {
    Emotion,
    Journal,
    Recommendation,
}