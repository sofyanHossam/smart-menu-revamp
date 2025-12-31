package com.example.smartmenu.openai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val api = OpenAIClient.retrofit.create(OpenAIService::class.java)

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Idle)
    val uiState: StateFlow<ChatUiState> = _uiState

    fun sendMessage(text: String) {
        viewModelScope.launch {
            _uiState.value = ChatUiState.Loading

            try {
                val response = api.sendMessage(
                    ChatRequest(
                        model = "gpt-3.5-turbo",
                        messages = listOf(
                            Message("user", text)
                        )
                    )
                )

                val reply = response.choices.first().message.content
                _uiState.value = ChatUiState.Success(reply)

            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
