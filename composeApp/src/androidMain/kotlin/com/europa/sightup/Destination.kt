package com.europa.sightup

/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector


interface Destination {
    val icon: ImageVector
    val route: String
}

object Overview : Destination {
    override val icon = Icons.Filled.Home
    override val route = "overview"
}

object EyeExercise : Destination {
    override val icon = Icons.Filled.Face
    override val route = "eye exercise"
}

val TabRowScreens = listOf(Overview, EyeExercise)
