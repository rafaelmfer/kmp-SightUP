package com.europa.sightup.data.network

import com.europa.sightup.data.local.KVaultStorage
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {

    private const val TIMEOUT = 90000L
    const val JWT_TOKEN = "TOKEN"

    fun provideHttpClient(kVaultStorage: KVaultStorage): HttpClient {
        val httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT
                connectTimeoutMillis = TIMEOUT
                socketTimeoutMillis = TIMEOUT
            }
//            install(Logging) {
//                level = LogLevel.BODY
//            }

            defaultRequest {
                val token = kVaultStorage.get(JWT_TOKEN)
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
            }
        }

        return httpClient
    }

    fun provideKtorfit(baseUrl: String, httpClient: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(baseUrl)
            .httpClient(httpClient)
            .build()
    }
}