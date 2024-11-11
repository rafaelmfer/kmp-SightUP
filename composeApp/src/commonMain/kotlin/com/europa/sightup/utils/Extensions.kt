package com.europa.sightup.utils

import androidx.compose.runtime.Composable
import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.data.local.getObject
import com.europa.sightup.data.network.NetworkClient.JWT_TOKEN
import com.europa.sightup.data.remote.response.UserResponse
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.koinInject

val isUserLoggedIn: Boolean
    @Composable
    get() {
        val kVault = koinInject<KVaultStorage>()

        val user = kVault.getObject<UserResponse>(USER_INFO)
        val token = kVault.get(JWT_TOKEN)

        return user != null && token.isNotBlank()
    }

fun Clock.System.currentTimeMillis(): Long {
    return now().toEpochMilliseconds()
}

fun String.encodeForUrl(): String {
    return this.replace(" ", "%20")
        .replace("!", "%21")
        .replace("#", "%23")
        .replace("$", "%24")
        .replace("&", "%26")
        .replace("'", "%27")
        .replace("(", "%28")
        .replace(")", "%29")
        .replace("*", "%2A")
        .replace("+", "%2B")
        .replace(",", "%2C")
        .replace("/", "%2F")
        .replace(":", "%3A")
        .replace(";", "%3B")
        .replace("=", "%3D")
        .replace("?", "%3F")
        .replace("@", "%40")
        .replace("[", "%5B")
        .replace("]", "%5D")
}

fun String.capitalizeWords(): String = lowercase().split(" ")
    .joinToString(" ") { words ->
        words.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase()
            } else {
                it.toString()
            }
        }
    }

fun String.toFormattedDate(outputFormat: String = "MMM dd, yyyy"): String {
    val instant = Instant.parse(this)

    val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val year = localDate.year
    val month = localDate.monthNumber.toString().padStart(2, '0')
    val monthShortName = localDate.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val day = localDate.dayOfMonth.toString().padStart(2, '0')

    return outputFormat
        .replace("MMM", monthShortName)
        .replace("MM", month)
        .replace("dd", day)
        .replace("yyyy", year.toString())
}


fun String.formatTime(): String {
    val instant = Instant.parse(this)

    val timeZone = TimeZone.currentSystemDefault()

    val localDateTime = instant.toLocalDateTime(timeZone)

    return localDateTime.hour.toString().padStart(2, '0') + ":" +
        localDateTime.minute.toString().padStart(2, '0') +
        " " + (if (localDateTime.hour < 12) "am" else "pm")
}


fun getTodayDateString(format: String = "MMM dd, yyyy"): String {
    val currentInstant = Clock.System.now()

    val localDate = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val year = localDate.year
    val month = localDate.monthNumber.toString().padStart(2, '0')
    val monthShortName = localDate.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val day = localDate.dayOfMonth.toString().padStart(2, '0')

    return format
        .replace("MMM", monthShortName)
        .replace("MM", month)
        .replace("dd", day)
        .replace("yyyy", year.toString())
}