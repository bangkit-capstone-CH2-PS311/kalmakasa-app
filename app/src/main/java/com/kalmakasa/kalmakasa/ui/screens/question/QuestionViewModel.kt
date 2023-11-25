package com.kalmakasa.kalmakasa.ui.screens.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class QuestionViewModel : ViewModel() {

    val questions = listOf(
        "I found it hard to wind down",
        "I was aware of dryness of my mouth",
//        "I couldn’t seem to experience any positive feeling at all",
//        "I experienced breathing difficulty (e.g. excessively rapid breathing, breathlessness in the absence of physical exertion)",
//        "I found it difficult to work up the initiative to do things",
//        "I tended to over-react to situations",
//        "I experienced trembling (e.g. in the hands)",
//        "I felt that I was using a lot of nervous energy",
//        "I was worried about situations in which I might panic and make a fool of myself",
//        "I felt that I had nothing to look forward to",
//        "I found myself getting agitated",
//        "I found it difficult to relax",
//        "I felt down-hearted and blue",
//        "I was intolerant of anything that kept me from getting on with what I was doing",
//        "I felt I was close to panic",
//        "I was unable to become enthusiastic about anything",
//        "I felt I wasn’t worth much as a person",
//        "I felt that I was rather touchy",
//        "I was aware of the action of my heart in the absence of physical exertion (e.g. sense of heart rate increase, heart missing a beat)",
//        "I felt scared without any good reason",
//        "I felt that life was meaningless",
    )

    val options = listOf(
        "Strongly Agree",
        "Agree",
        "Disagree",
        "Strongly Disagree",
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
                isDone = currentIndex == questions.size,
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
    val isDone: Boolean = false,
)