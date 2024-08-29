package com.willaapps.shop.data.mappers

import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.Word
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.data.dto.BookDto
import com.willaapps.shop.data.dto.WordDto
import com.willaapps.shop.data.dto.WordSetDto
import com.willaapps.shop.domain.model.ShopWordSet

fun BookDto.toBook(): Book {
    return Book(
        name = name,
        color = color,
        bookId = id
    )
}

fun WordSetDto.toWordSet(): WordSet {
    return WordSet(
        name = name,
        bookId = bookId,
        numberOfGroups = numberOfGroups,
        id = id
    )
}

fun WordDto.toWord(): Word {
    return Word(
        sentence = sentence,
        wordPl = wordPl,
        wordEng = wordEng,
        setId = setId,
        groupNumber = groupNumber,
        id = id
    )
}

fun WordSet.toShopWordSet(isDownloaded: Boolean): ShopWordSet {
    return ShopWordSet(
        name = name,
        bookId = bookId,
        numberOfGroups = numberOfGroups,
        id = id,
        isDownloaded = isDownloaded
    )
}