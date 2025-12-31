package com.example.smartmenu.speechToText

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartmenu.R


class SpeechToTextActivity : ComponentActivity(),OnRecognitionListener {
    private lateinit var speechToTextConverter: SpeechToTextConverter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        speechToTextConverter = SpeechToTextConverter(this,this)

        requestAudioPermission()

        setContent {
            SpeechToTextScreen(
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
    }

    override fun onResults(results: String) {
        MicUiState.micState.value = MicState.IDLE
        MicUiState.text.value = results
    }
}