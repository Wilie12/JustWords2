package com.willaapps.word.data.previous

import com.willaapps.word.domain.PreviousWord

fun PreviousWord.toPreviousWordSerializable(): PreviousWordSerializable {
    return PreviousWordSerializable(
        bookId = bookId,
        bookColor = bookColor,
        bookName = bookName,
        setId = setId,
        setName = setName,
        groupNumber = groupNumber
    )
}

fun PreviousWordSerializable.toPreviousWord(): PreviousWord {
    return PreviousWord(
        bookId = bookId,
        bookColor = bookColor,
        bookName = bookName,
        setId = setId,
        setName = setName,
        groupNumber = groupNumber
    )
}