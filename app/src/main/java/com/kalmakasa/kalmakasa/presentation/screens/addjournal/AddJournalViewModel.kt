package com.kalmakasa.kalmakasa.presentation.screens.addjournal

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddJournalViewModel : ViewModel() {

    private val currentStepIndex = 0

    private val _currentStep = MutableStateFlow(journalStep.first())
    val currentStep: StateFlow<JournalStep> = _currentStep.asStateFlow()

    companion object {
        private val journalStep = listOf(
            JournalStep.Journal,
            JournalStep.Emotion
        )
    }

}

enum class JournalStep {
    Emotion,
    Journal
}