package com.willaapps.shop.data

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.asEmptyDataResult
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.data.mappers.toShopWordSet
import com.willaapps.shop.domain.RemoteShopDataSource
import com.willaapps.shop.domain.model.ShopBook
import com.willaapps.shop.domain.ShopRepository
import com.willaapps.shop.domain.model.ShopWordSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ShopRepositoryImpl(
    private val remoteShopDataSource: RemoteShopDataSource,
    private val localWordDataSource: LocalWordDataSource,
    private val applicationScope: CoroutineScope
) : ShopRepository {
    override suspend fun getShopBooks(): Result<List<ShopBook>, DataError.Network> {
        var books: List<Book> = emptyList()
        var wordSets: List<WordSet> = emptyList()
        val shopBooks: MutableList<ShopBook> = mutableListOf()

        when (val booksResult = remoteShopDataSource.getBooks()) {
            is Result.Error -> {
                return Result.Error(booksResult.error)
            }
            is Result.Success -> {
                books = booksResult.data
            }
        }
        when (val wordSetResult = remoteShopDataSource.getWordSets()) {
            is Result.Error -> {
                return Result.Error(wordSetResult.error)
            }
            is Result.Success -> {
                wordSets = wordSetResult.data
            }
        }

        books.forEach { book ->
            val wordSetsNames = wordSets
                .filter { it.bookId == book.bookId }
                .map { it.name }

            shopBooks += ShopBook(
                book = book,
                setNames = wordSetsNames
            )
        }

        return Result.Success(shopBooks)
    }

    override suspend fun getBookById(bookId: String): Result<Book, DataError.Network> {
        return when (val result = remoteShopDataSource.getBooks()) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> {
                Result.Success(result.data.first { it.bookId == bookId })
            }
        }
    }

    override fun getShopWordSets(bookId: String): Flow<List<ShopWordSet>> {
        return localWordDataSource.getWordSetsById(bookId)
            .map { wordSets ->
                wordSets.map { wordSet -> wordSet.toShopWordSet(isDownloaded = true) }
            }
            .combine(getRemoteWordSetsFlow(bookId)) { localList, remoteList ->
                val localListMutable = localList.toMutableList()
                remoteList.forEach { remoteWordSet ->
                    if (remoteWordSet.copy(isDownloaded = true) !in localList) {
                        localListMutable += remoteWordSet
                    }
                }
                localListMutable.toList()
            }
    }

    override suspend fun fetchWords(book: Book, wordSet: WordSet): EmptyResult<DataError> {
        return when (val result = remoteShopDataSource.getWordsById(wordSet.id)) {
            is Result.Error -> {
                result.asEmptyDataResult()
            }
            is Result.Success -> {
                applicationScope.async {
                    localWordDataSource.insertWords(result.data).asEmptyDataResult()
                }.await()
                applicationScope.async {
                    localWordDataSource.insertWordSet(wordSet).asEmptyDataResult()
                }.await()
                applicationScope.async {
                    localWordDataSource.insertBook(book).asEmptyDataResult()
                }.await()
            }
        }
    }

    private fun getRemoteWordSetsFlow(bookId: String) = flow<List<ShopWordSet>> {
        when (val result = remoteShopDataSource.getWordSetsById(bookId)) {
            is Result.Error -> {
                emit(emptyList())
            }
            is Result.Success -> {
                val resultMapped = result.data.map { wordSet ->
                    wordSet.toShopWordSet(isDownloaded = false)
                }
                emit(resultMapped)
            }
        }
    }
}