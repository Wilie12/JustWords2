package com.willaapps.word.presentation.start

import com.willaapps.core.domain.word.Book
import com.willaapps.word.domain.PreviousWord

data class StartState(
    val userName: String = "",
    val previousWord: PreviousWord? = null,
    val dailyGoalAim: Int = 4,
    val dailyGoalCurrent: Int = 0,
    val books: List<Book> = emptyList()
)
