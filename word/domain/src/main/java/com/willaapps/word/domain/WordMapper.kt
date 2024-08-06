package com.willaapps.word.domain

import com.willaapps.core.domain.word.Word

fun Word.toWordGuessable(): WordGuessable {
    return WordGuessable(
        sentence = sentence,
        wordPl = wordPl,
        wordEng = wordEng
    )
}