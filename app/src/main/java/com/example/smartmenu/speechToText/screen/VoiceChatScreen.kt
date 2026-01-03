package com.example.smartmenu.speechToText.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartmenu.speechToText.state.ChatUiState
import com.example.smartmenu.speechToText.viewmodel.ChatViewModel
import com.example.smartmenu.speechToText.state.MicState
import com.example.smartmenu.speechToText.state.MicUiState

@Composable
fun VoiceChatScreen(
    onMicClick: () -> Unit,
    viewModel: ChatViewModel = viewModel()
) {

    val micState by MicUiState.micState
    val spokenText by MicUiState.text
    val isFinal by MicUiState.isFinalResult
    val uiState by viewModel.uiState.collectAsState()
    val isLoading = uiState is ChatUiState.Loading

    val micColor = when (micState) {
        MicState.IDLE -> Color.Gray
        MicState.LISTENING -> Color.Green
        MicState.ERROR -> Color.Red
    }

    LaunchedEffect(isFinal) {
        if (isFinal && spokenText.isNotBlank()) {
            viewModel.sendMessage(spokenText)
            MicUiState.isFinalResult.value = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 🎤 Mic Button
        IconButton(
            onClick = onMicClick,
            enabled = !isLoading,
            modifier = Modifier.size(96.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Mic",
                tint = micColor,
                modifier = Modifier.size(96.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🗣️ Spoken Text
        if (spokenText.isNotBlank()) {
            Text(
                text = spokenText,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🤖 Chat Result
        when (uiState) {

            is ChatUiState.Idle -> {
                Text("اضغط على المايك وابدأ كلامك")
            }

            is ChatUiState.Loading -> {

                CircularProgressIndicator()
            }

            is ChatUiState.Success -> {
                Text(
                    text = (uiState as ChatUiState.Success).message,
                    fontSize = 18.sp
                )
            }

            is ChatUiState.Error -> {
                Text(
                    text = (uiState as ChatUiState.Error).error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
