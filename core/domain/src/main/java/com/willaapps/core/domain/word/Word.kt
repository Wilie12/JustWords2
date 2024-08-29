package com.willaapps.core.domain.word

data class Word(
    val sentence: String,
    val wordPl: String,
    val wordEng: String,
    val setId: String,
    val groupNumber: Int,
    val id: String
)
