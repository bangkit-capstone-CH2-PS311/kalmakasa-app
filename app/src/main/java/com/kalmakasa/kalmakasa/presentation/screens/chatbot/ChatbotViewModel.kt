package com.kalmakasa.kalmakasa.presentation.screens.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalmakasa.kalmakasa.common.Resource
import com.kalmakasa.kalmakasa.domain.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatbotViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
) : ViewModel() {
    val messages = messageRepository.getMessages().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val messageState: MutableStateFlow<Resource<Boolean>> =
        MutableStateFlow(Resource.Success(false))

    fun sendMessage(message: String) {
        viewModelScope.launch {
            messageRepository.sendMessage(message).collect {
                messageState.value = it
            }
        }
    }

}
