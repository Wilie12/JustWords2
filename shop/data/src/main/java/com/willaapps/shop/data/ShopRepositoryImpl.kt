package com.willaapps.shop.data

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.domain.RemoteShopDataSource
import com.willaapps.shop.domain.ShopBook
import com.willaapps.shop.domain.ShopRepository
import com.willaapps.shop.domain.ShopWordSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ShopRepositoryImpl(
    private val remoteShopDataSource: RemoteShopDataSource,
    private val localWordDataSource: LocalWordDataSource
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