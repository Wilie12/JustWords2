package com.willaapps.auth.domain

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Network>

    suspend fun login(
        email: String,
        password: String
    ): EmptyResult<DataError.Network>
}