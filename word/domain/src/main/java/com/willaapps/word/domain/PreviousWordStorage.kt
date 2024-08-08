package com.willaapps.word.domain

import kotlinx.coroutines.flow.Flow

interface PreviousWordStorage {

    fun get(): Flow<PreviousWord?>
    suspend fun set(previousWord: PreviousWord?)
}