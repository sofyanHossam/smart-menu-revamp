package com.example.smartmenu.openai

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {
    var inputText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔹 TextField
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("اكتب رسالتك") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Button
        Button(
            onClick = {
                if (inputText.isNotBlank()) {
                    viewModel.sendMessage(inputText)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("إرسال")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Result Area
        when (uiState) {
            is ChatUiState.Idle -> {
                Text("اكتب رسالة واضغط إرسال")
            }

            is ChatUiState.Loading -> {
                CircularProgressIndicator()
            }

            is ChatUiState.Success -> {
                Text(
                    text = (uiState as ChatUiState.Success).message
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
