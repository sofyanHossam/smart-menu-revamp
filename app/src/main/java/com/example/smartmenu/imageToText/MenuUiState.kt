package com.example.smartmenu.imageToText

sealed class MenuUiState {
    object Idle : MenuUiState()
    object Loading : MenuUiState()
    data class Success(val items: List<MenuItem>) : MenuUiState()
    data class Error(val message: String) : MenuUiState()
}
