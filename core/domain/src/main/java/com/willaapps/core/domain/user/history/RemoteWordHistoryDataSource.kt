package com.willaapps.core.domain.user.history

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result

interface RemoteWordHistoryDataSource {

    suspend fun getWordHistoryItems(): Result<List<WordHistory>, DataError.Network>
    suspend fun postWordHistory(wordHistory: WordHistory): EmptyResult<DataError.Network>
}