package com.europa.sightup.presentation.screens.test

interface VoiceRecognition {
    fun startListening(onResult: (String) -> Unit)
    fun stopListening()
}

expect fun getVoiceRecognition(context: Any? = null): VoiceRecognition