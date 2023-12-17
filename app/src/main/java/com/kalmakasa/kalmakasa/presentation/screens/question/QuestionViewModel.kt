package com.kalmakasa.kalmakasa.presentation.screens.question

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.R
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.data.network.DassDataSource
import com.kalmakasa.kalmakasa.domain.repository.HealthTestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val healthTestRepository: HealthTestRepository,
) : ViewModel() {

    val questions = DassDataSource.questions

    private val uploadState: MutableStateFlow<Resource<String>?> = MutableStateFlow(null)

    val options = listOf(
        R.string.strongly_agree,
        R.string.agree,
        R.string.disagree,
        R.string.strongly_disagree,
    )
    private val _currentQuestionIndex = MutableStateFlow(1)

    private val _answers = MutableStateFlow(emptyMap<Int, String>())

    val uiState: StateFlow<QuestionScreenData> =
        combine(
            _currentQuestionIndex,
            _answers,
            uploadState
        ) { currentIndex, answers, uploadState ->
            QuestionScreenData(
                currentQuestionIndex = currentIndex,
                currentQuestion = questions[currentIndex - 1],
                currentAnswer = answers[currentIndex],
                progress = currentIndex / questions.size.toFloat(),
                lastQuestion = currentIndex == questions.size,
                uploadState = uploadState,
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

    fun uploadAnswer(
        onSuccessCallback: () -> Unit,
        onErrorCallback: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            healthTestRepository.createHealthTest().collect {
                when (it) {
                    is Resource.Error -> {
                        uploadState.value = it
                        onErrorCallback?.let {
                            onErrorCallback()
                        }
                    }

                    Resource.Loading -> {
                        uploadState.value = Resource.Loading
                        Log.d("tag", "Loading")
                    }

                    is Resource.Success -> {
                        uploadState.value = Resource.Success("Success")
                        onSuccessCallback()
                    }
                }
            }
        }
    }

}

data class QuestionScreenData(
    val currentQuestionIndex: Int = 1,
    val currentQuestion: Int = R.string.question1,
    val currentAnswer: String? = null,
    val progress: Float = 0F,
    val lastQuestion: Boolean = false,
    val uploadState: Resource<String>? = null,
)