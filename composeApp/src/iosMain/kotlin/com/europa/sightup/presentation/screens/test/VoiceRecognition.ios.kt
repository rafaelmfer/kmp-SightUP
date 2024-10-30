package com.europa.sightup.presentation.screens.test

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioEngine
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryRecord
import platform.AVFAudio.AVAudioSessionModeMeasurement
import platform.AVFAudio.setActive
import platform.Foundation.NSLocale
import platform.Speech.SFSpeechAudioBufferRecognitionRequest
import platform.Speech.SFSpeechRecognitionTask
import platform.Speech.SFSpeechRecognizer
import platform.Speech.SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusAuthorized
import platform.Speech.SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusDenied
import platform.Speech.SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusNotDetermined
import platform.Speech.SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusRestricted

actual fun getVoiceRecognition(context: Any?): VoiceRecognition = IOSVoiceRecognition()

class IOSVoiceRecognition : VoiceRecognition {

    private val audioEngine = AVAudioEngine()
    private val speechRecognizer = SFSpeechRecognizer(locale = NSLocale(localeIdentifier = "en-US"))
    private var recognitionRequest: SFSpeechAudioBufferRecognitionRequest? = null
    private var recognitionTask: SFSpeechRecognitionTask? = null
    private var isListening = false

    override fun startListening(onResult: (String) -> Unit) {
        if (isListening) return
        if (!speechRecognizer.isAvailable()) {
            onResult("Speech recognition is not available")
            println("Speech recognition is not available")
            return
        }

        // Request permission
        SFSpeechRecognizer.requestAuthorization { authStatus ->
            when (authStatus) {
                SFSpeechRecognizerAuthorizationStatusAuthorized -> {
                    startRecording(onResult)
                }

                SFSpeechRecognizerAuthorizationStatusDenied -> {
                    onResult("Permission denied for speech recognition")
                    println("Permission denied for speech recognition")
                }

                SFSpeechRecognizerAuthorizationStatusRestricted -> {
                    onResult("Speech recognition is restricted on this device")
                    println("Speech recognition is restricted on this device")
                }

                SFSpeechRecognizerAuthorizationStatusNotDetermined -> {
                    onResult("Speech recognition permission not determined")
                    println("Speech recognition permission not determined")
                }

                else -> {
                    onResult("Unknown authorization status")
                    println("Unknown authorization status")
                }
            }
        }
    }

    override fun stopListening() {
        stopCurrentRecognition()
        isListening = false
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun startRecording(onResult: (String) -> Unit) {
        recognitionTask?.cancel()
        recognitionTask = null

        recognitionRequest = SFSpeechAudioBufferRecognitionRequest()
        recognitionRequest?.shouldReportPartialResults = true
        isListening = true

        val inputNode = audioEngine.inputNode
        val audioSession = AVAudioSession.sharedInstance()
        audioSession.setCategory(AVAudioSessionCategoryRecord, error = null)
        audioSession.setMode(AVAudioSessionModeMeasurement, error = null)
        audioSession.setActive(true, error = null)

        recognitionTask = speechRecognizer.recognitionTaskWithRequest(recognitionRequest!!) { result, error ->
            if (result != null) {
                val bestTranscription = result.bestTranscription.formattedString
                onResult(bestTranscription)
            }

            if (error != null || result?.isFinal() == true) {
                stopCurrentRecognition()
                restartListening(onResult)
            }
        }

        val recordingFormat = inputNode.inputFormatForBus(0u)
        inputNode.installTapOnBus(0u, bufferSize = 1024u, format = recordingFormat) { buffer, _ ->
            if (buffer != null) {
                recognitionRequest?.appendAudioPCMBuffer(buffer)
            }
        }

        audioEngine.prepare()
        audioEngine.startAndReturnError(null)
    }

    private fun stopCurrentRecognition() {
        audioEngine.stop()
        audioEngine.inputNode.removeTapOnBus(0u)
        recognitionRequest?.endAudio()
        recognitionRequest = null
        recognitionTask?.cancel()
        recognitionTask = null
        isListening = false
    }

    private fun restartListening(onResult: (String) -> Unit) {
        stopCurrentRecognition()
        startListening(onResult)
    }
}