package com.kalmakasa.kalmakasa.presentation.screens.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.data.network.DassDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class QuestionViewModel : ViewModel() {

    val questions = DassDataSource.questions

    val options = listOf(
        R.string.strongly_agree,
        R.string.agree,
        R.string.disagree,
        R.string.strongly_disagree,
    )
    private val _currentQuestionIndex = MutableStateFlow(1)

    private val _answers = MutableStateFlow(emptyMap<Int, String>())

    val uiState: StateFlow<QuestionScreenData> =
        combine(_currentQuestionIndex, _answers) { currentIndex, answers ->
            QuestionScreenData(
                currentQuestionIndex = currentIndex,
                currentQuestion = questions[currentIndex - 1],
                currentAnswer = answers[currentIndex],
                progress = currentIndex / questions.size.toFloat(),
                lastQuestion = currentIndex == questions.size,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = QuestionScreenData()
        )


    fun nextQuestion() {
        if (uiState.value.currentQuestionIndex < questions.size) {
            _currentQuestionIndex.value++
        }
    }

    fun previousQuestion() {
        if (uiState.value.currentQuestionIndex > 1) {
            _currentQuestionIndex.value--
        }
    }

    fun updateAnswer(answer: String) {
        _answers.update { answers ->
            val newAnswers = answers.toMutableMap()
            newAnswers[_currentQuestionIndex.value] = answer
            newAnswers
        }
    }

}

data class QuestionScreenData(
    val currentQuestionIndex: Int = 1,
    val currentQuestion: String = "",
    val currentAnswer: String? = null,
    val progress: Float = 0F,
    val lastQuestion: Boolean = false,
)