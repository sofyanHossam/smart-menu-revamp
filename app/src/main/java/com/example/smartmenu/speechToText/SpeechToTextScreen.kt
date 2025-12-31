package com.example.smartmenu.speechToText

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpeechToTextScreen(
    onMicClick: () -> Unit
) {
    val micState by MicUiState.micState
    val text by MicUiState.text

    val micColor = when (micState) {
        MicState.IDLE -> Color.Gray
        MicState.LISTENING -> Color.Green
        MicState.ERROR -> Color.Red
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(
            onClick = onMicClick,
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

        Text(
            text = text,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}
