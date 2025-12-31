package com.example.smartmenu.openai

sealed class ChatUiState {
    object Idle : ChatUiState()
    object Loading : ChatUiState()
    data class Success(val message: String) : ChatUiState()
    data class Error(val error: String) : ChatUiState()
}
