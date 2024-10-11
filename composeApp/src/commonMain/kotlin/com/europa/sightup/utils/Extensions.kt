package com.europa.sightup.utils

import kotlinx.datetime.Clock

fun Clock.System.currentTimeMillis(): Long {
    return now().toEpochMilliseconds()
}