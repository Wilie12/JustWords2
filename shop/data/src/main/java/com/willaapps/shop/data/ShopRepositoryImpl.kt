package com.willaapps.shop.data

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.domain.RemoteShopDataSource
import com.willaapps.shop.domain.ShopBook
import com.willaapps.shop.domain.ShopRepository

class ShopRepositoryImpl(
    private val remoteShopDataSource: RemoteShopDataSource
): ShopRepository {
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
}