package com.example.smartmenu.speechToText

import androidx.compose.runtime.mutableStateOf

object MicUiState {
    val micState = mutableStateOf(MicState.IDLE)
    val text = mutableStateOf("اضغط على الميكروفون وابدأ الكلام")
}