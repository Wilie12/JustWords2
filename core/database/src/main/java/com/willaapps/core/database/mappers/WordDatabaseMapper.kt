package com.willaapps.core.database.mappers

import com.willaapps.core.database.entity.BookEntity
import com.willaapps.core.database.entity.SetEntity
import com.willaapps.core.database.entity.WordEntity
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.Word
import com.willaapps.core.domain.word.WordSet

fun BookEntity.toBook(): Book {
    return Book(
        name = name,
        bookId = id,
        color = color
    )
}

fun SetEntity.toWordSet(): WordSet {
    return WordSet(
        name = name,
        bookId = bookId,
        numberOfGroups = numberOfGroups,
        id = id
    )
}

fun WordEntity.toWord(): Word {
    return Word(
        sentence = sentence,
        wordPl = wordPl,
        wordEng = wordEng
    )
}