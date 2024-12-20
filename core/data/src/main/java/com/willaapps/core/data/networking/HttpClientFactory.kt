package com.willaapps.core.data.networking

import com.willaapps.core.domain.auth.AuthInfo
import com.willaapps.core.domain.auth.SessionStorage
import com.willaapps.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage
) {

    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        println("LOADING TOKENS")
                        val info = sessionStorage.get()
                        println("INFO LOADED: $info")
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: ""
                        ).also {
                            println("BEARER TOKENS: $it")
                        }
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = "/accessToken",
                            body = AccessTokenRequest(
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: ""
                            )
                        )

                        when (response) {
                            is Result.Success -> {
                                val newAuthInfo = AuthInfo(
                                    accessToken = response.data.accessToken,
                                    refreshToken = info?.refreshToken ?: "",
                                    userId = info?.userId ?: ""
                                )
                                sessionStorage.set(newAuthInfo)

                                BearerTokens(
                                    accessToken = newAuthInfo.accessToken,
                                    refreshToken = newAuthInfo.refreshToken
                                )
                            }
                            is Result.Error -> {
                                BearerTokens(
                                    accessToken = "",
                                    refreshToken = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}