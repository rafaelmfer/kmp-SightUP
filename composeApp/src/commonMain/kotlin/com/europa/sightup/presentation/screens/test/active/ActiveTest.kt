package com.europa.sightup.presentation.screens.test.active

enum class ActiveTest(
    val startAudio: String,
    val distanceLottie: String,
) {
    VisualAcuity(
        startAudio = "visual_acuity_start_screen.m4a",
        distanceLottie = "files/distance_visual_acuity.json"
    ),
    Astigmatism(
        startAudio = "astigmatism_start_screen.m4a",
        distanceLottie = "files/distance_astigmatism.json"
    )
}