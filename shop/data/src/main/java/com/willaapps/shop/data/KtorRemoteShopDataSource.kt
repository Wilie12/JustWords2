package com.willaapps.shop.data

import com.willaapps.core.data.networking.get
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.map
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.domain.RemoteShopDataSource
import io.ktor.client.HttpClient

class KtorRemoteShopDataSource(
    private val httpClient: HttpClient
): RemoteShopDataSource {
    override suspend fun getBooks(): Result<List<Book>, DataError.Network> {
        return httpClient.get<BooksResponse>(
            route = "/books"
        ).map { bookResponse ->
            bookResponse.books.map { it.toBook() }
        }
    }

    override suspend fun getWordSets(): Result<List<WordSet>, DataError.Network> {
        return httpClient.get<WordSetResponse>(
            route = "/sets"
        ).map { wordSetResponse ->
            wordSetResponse.sets.map { it.toWordSet() }
        }
    }
}