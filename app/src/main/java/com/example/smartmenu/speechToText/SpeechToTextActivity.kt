package com.example.smartmenu.speechToText

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartmenu.similarity.SmartTextMatcher
import com.example.smartmenu.speechToText.contract.OnRecognitionListener
import com.example.smartmenu.speechToText.helper.SpeechToTextConverter
import com.example.smartmenu.speechToText.screen.VoiceChatScreen
import com.example.smartmenu.speechToText.state.MicState
import com.example.smartmenu.speechToText.state.MicUiState


class SpeechToTextActivity : ComponentActivity(), OnRecognitionListener {

    private lateinit var speechToTextConverter: SpeechToTextConverter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        speechToTextConverter = SpeechToTextConverter(this, this)
        requestAudioPermission()

        val matcher = SmartTextMatcher()
        val userInputs = listOf(
            "شرما فراخ",
            "فول",
            "بطاطس"
        )

        val menuItems = listOf(
            "شاورما فراخ",
            "شاورما لحمة",
            "فول مدمس",
            "بطاطس محمرة",
            "برجر فراخ"
        )

        val result = matcher.match(userInputs, menuItems)

        result.forEach { (userInput, matches) ->
            Log.d("SmartMatcher", "User Input: $userInput")

            if (matches.isEmpty()) {
                Log.d("SmartMatcher", "  ❌ No matches found")
            } else {
                matches.forEachIndexed { index, match ->
                    Log.d(
                        "SmartMatcher",
                        "  ${index + 1}) ${match.menuItem}  → score = ${"%.2f".format(match.score)}"
                    )
                }
            }
        }

        setContent {
            VoiceChatScreen(
                onMicClick = {
                    speechToTextConverter.startListening("ar")
                }
            )
        }
    }

    private fun requestAudioPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                100
            )
        }
    }

    override fun onReadyForSpeech() {
        MicUiState.micState.value = MicState.LISTENING
    }

    override fun onBeginningOfSpeech() {
        MicUiState.micState.value = MicState.LISTENING
    }

    override fun onEndOfSpeech() {
        MicUiState.micState.value = MicState.IDLE
    }

    override fun onError(error: String) {
        MicUiState.micState.value = MicState.ERROR
        MicUiState.text.value = error
        MicUiState.isFinalResult.value = false
    }

    override fun onResults(results: String) {
        MicUiState.micState.value = MicState.IDLE
        MicUiState.text.value = results
        MicUiState.isFinalResult.value = true
    }
}
