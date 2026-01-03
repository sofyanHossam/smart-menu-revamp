package com.example.smartmenu.speechToText.contract

interface OnRecognitionListener {
    fun onReadyForSpeech()
    fun onBeginningOfSpeech()
    fun onEndOfSpeech()
    fun onError(error: String)
    fun onResults(results: String)
}