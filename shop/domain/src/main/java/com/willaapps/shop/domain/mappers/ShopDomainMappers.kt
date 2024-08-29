package com.willaapps.shop.domain.mappers

import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.domain.model.ShopWordSet

fun ShopWordSet.toWordSet(): WordSet {
    return WordSet(
        name = name,
        bookId = bookId,
        numberOfGroups = numberOfGroups,
        id = id
    )
}