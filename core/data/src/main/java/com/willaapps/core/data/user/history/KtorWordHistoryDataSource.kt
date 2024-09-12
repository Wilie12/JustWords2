package com.willaapps.core.data.user.history

import com.willaapps.core.data.networking.get
import com.willaapps.core.data.networking.post
import com.willaapps.core.domain.user.history.RemoteWordHistoryDataSource
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.map
import io.ktor.client.HttpClient

class KtorWordHistoryDataSource(
    private val httpClient: HttpClient
): RemoteWordHistoryDataSource {
    override suspend fun getWordHistoryItems(): Result<List<WordHistory>, DataError.Network> {
        return httpClient.get<List<WordHistoryDto>>(
            route = "/userHistory"
        ).map { wordHistoryDtos ->
            wordHistoryDtos.map { it.toWordHistory() }
        }
    }

    override suspend fun postWordHistory(wordHistory: WordHistory): EmptyResult<DataError.Network> {
        return httpClient.post<WordHistoryDto, Unit>(
            route = "/postUserHistory",
            body = wordHistory.toWordHistoryDto()
        )
    }
}