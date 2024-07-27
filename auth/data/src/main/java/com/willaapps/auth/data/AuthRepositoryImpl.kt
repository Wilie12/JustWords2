package com.willaapps.auth.data

import com.willaapps.auth.domain.AuthRepository
import com.willaapps.core.data.networking.post
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository {

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )
    }
}