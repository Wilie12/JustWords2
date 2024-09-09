package com.willaapps.word.presentation.start

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.willaapps.core.domain.word.Book
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.ShopIcon
import com.willaapps.core.presentation.designsystem.UserIcon
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.word.domain.PreviousWord
import com.willaapps.word.presentation.R
import com.willaapps.word.presentation.start.components.BookItem
import com.willaapps.word.presentation.start.components.DailyGoalBox
import com.willaapps.word.presentation.start.components.PreviousBox
import com.willaapps.word.presentation.start.components.StartToolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun StartScreenRoot(
    onUserClick: () -> Unit,
    onShopClick: () -> Unit,
    onBookClick: (String) -> Unit,
    onPreviousClick: (
        bookId: String,
        bookColor: Int,
        setId: String,
        groupNumber: Int
    ) -> Unit,
    viewModel: StartViewModel = koinViewModel()
) {
    StartScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is StartAction.OnBookClick -> onBookClick(action.bookId)
                StartAction.OnShopClick -> onShopClick()
                StartAction.OnUserClick -> onUserClick()
                is StartAction.OnPreviousClick -> onPreviousClick(
                    action.bookId,
                    action.bookColor,
                    action.setId,
                    action.groupNumber
                )
            }
        }
    )
}

@Composable
fun StartScreen(
    state: StartState,
    onAction: (StartAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GradientBall(
                gradientColor = Color(0xFF119DA4),
                offsetY = 200f
            )
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            StartToolbar(
                text = stringResource(id = R.string.welcome, state.userName),
                icon = UserIcon,
                onIconClick = {
                    onAction(StartAction.OnUserClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
            )
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(visible = !state.isLoading) {
                DailyGoalBox(
                    dailyGoalAim = state.dailyGoalAim,
                    dailyGoalCurrent = state.dailyGoalCurrent
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (state.previousWord != null) {
                PreviousBox(
                    previousWord = state.previousWord,
                    onPreviousWordClick = { bookId, bookColor, setId, groupNumber ->
                        onAction(StartAction.OnPreviousClick(bookId, bookColor, setId, groupNumber))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100f))
                    .weight(1f)
                    .background(color = Color(0xFFD7D9CE))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StartToolbar(
                        text = stringResource(id = R.string.choose_book),
                        textColor = Color(0xFF121211),
                        fontWeight = FontWeight.Normal,
                        icon = ShopIcon,
                        onIconClick = {
                            onAction(StartAction.OnShopClick)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clip(RoundedCornerShape(100f))
                ) {
                    items(
                        items = state.books,
                        key = { it.bookId }
                    ) { book ->
                        BookItem(
                            book = book,
                            onBookClick = {
                                onAction(StartAction.OnBookClick(bookId = book.bookId))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun StartScreenPreview() {
    JustWords2Theme {
        StartScreen(
            state = StartState(
                userName = "JohnDoe87",
                dailyGoalCurrent = 2,
                books = listOf(
                    Book(
                        name = "Work & Career",
                        bookId = "",
                        color = Color(0xFFD2614F).toArgb()
                    ),
                    Book(
                        name = "Health Care",
                        bookId = "",
                        color = Color(0xFF589BCC).toArgb()
                    ),
                    Book(
                        name = "Food & Cooking",
                        bookId = "",
                        color = Color(0xFF64A062).toArgb()
                    ),
                    Book(
                        name = "Personal Information",
                        bookId = "",
                        color = Color(0xFF964EA1).toArgb()
                    )
                ),
                previousWord = PreviousWord(
                    bookId = "1",
                    bookColor = Color(0xFF64A062).toArgb(),
                    bookName = "Food & Cooking",
                    setId = "1",
                    setName = "Ingredients",
                    groupNumber = 2
                )
            ),
            onAction = {}
        )
    }
}