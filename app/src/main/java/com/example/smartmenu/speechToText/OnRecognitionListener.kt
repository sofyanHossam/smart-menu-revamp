package com.example.smartmenu.speechToText

interface OnRecognitionListener {
    fun onReadyForSpeech()
    fun onBeginningOfSpeech()
    fun onEndOfSpeech()
    fun onError(error: String)
    fun onResults(results: String)
}