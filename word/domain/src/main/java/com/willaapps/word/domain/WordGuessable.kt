package com.willaapps.word.domain

data class WordGuessable(
    val sentence: String,
    val wordPl: String,
    val wordEng: String,
    val isFirstTimeSeen: Boolean = true
)
