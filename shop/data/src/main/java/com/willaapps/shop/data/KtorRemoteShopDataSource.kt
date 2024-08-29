package com.willaapps.shop.data

import com.willaapps.core.data.networking.get
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.map
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.Word
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.data.mappers.toBook
import com.willaapps.shop.data.mappers.toWord
import com.willaapps.shop.data.mappers.toWordSet
import com.willaapps.shop.data.response.BooksResponse
import com.willaapps.shop.data.response.WordSetResponse
import com.willaapps.shop.data.response.WordsResponse
import com.willaapps.shop.domain.RemoteShopDataSource
import io.ktor.client.HttpClient

class KtorRemoteShopDataSource(
    private val httpClient: HttpClient
): RemoteShopDataSource {
    override suspend fun getBooks(): Result<List<Book>, DataError.Network> {
        return httpClient.get<BooksResponse>(
            route = "/books"
        ).map { bookResponse -> bookResponse.books.map { it.toBook() } }
    }

    override suspend fun getWordSets(): Result<List<WordSet>, DataError.Network> {
        return httpClient.get<WordSetResponse>(
            route = "/sets"
        ).map { wordSetResponse -> wordSetResponse.sets.map { it.toWordSet() } }
    }

    override suspend fun getWordSetsById(bookId: String): Result<List<WordSet>, DataError.Network> {
        return httpClient.get<WordSetResponse>(
            route = "/setsById",
            queryParameters = mapOf(
                "bookId" to bookId
            )
        ).map { wordSetResponse -> wordSetResponse.sets.map { it.toWordSet() } }
    }

    override suspend fun getWordsById(setId: String): Result<List<Word>, DataError.Network> {
        return httpClient.get<WordsResponse>(
            route = "/words",
            queryParameters = mapOf(
                "setId" to setId
            )
        ).map { wordsResponse -> wordsResponse.words.map { it.toWord() } }
    }
}