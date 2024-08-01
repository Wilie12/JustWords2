package com.willaapps.word.presentation.start

import com.willaapps.core.domain.word.Book

data class StartState(
    val userName: String = "",
    val dailyGoalAim: Int = 4,
    val dailyGoalCurrent: Int = 0,
    val books: List<Book> = emptyList()
)
