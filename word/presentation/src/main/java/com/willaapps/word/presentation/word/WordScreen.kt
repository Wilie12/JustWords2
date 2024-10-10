@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.word.presentation.word

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.domain.word.Book
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.ActionButton
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwTextField
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import com.willaapps.word.domain.WordGuessable
import com.willaapps.word.presentation.R
import com.willaapps.word.presentation.word.util.hideWord
import org.koin.androidx.compose.koinViewModel

@Composable
fun WordScreenRoot(
    onBackClick: () -> Unit,
    onFinishClick: (bookId: String) -> Unit,
    viewModel: WordViewModel = koinViewModel()
) {
    WordScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                WordAction.OnBackClick -> onBackClick()
                is WordAction.OnButtonCLick -> {
                    when (action.buttonOption) {
                        ButtonOption.BUTTON_FINISH -> onFinishClick(viewModel.state.book.bookId)
                        else -> Unit
                    }
                }
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun WordScreen(
    state: WordState,
    onAction: (WordAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GradientBall(
            gradientColor = Color(state.book.color),
            offsetY = 200f
        )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                JwToolbar(
                    text = stringResource(R.string.words),
                    onBackClick = {
                        onAction(WordAction.OnBackClick)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .systemBarsPadding()
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (!state.isLoading) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(100f))
                            .background(Color(0xFFD7D9CE))
                            .padding(16.dp)
                            .animateContentSize()
                    ) {
                        when (state.buttonOption) {
                            ButtonOption.BUTTON_FINISH -> {
                                SummaryColumn(state = state)
                            }
                            else -> {
                                MainWordColumn(state = state)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        ActionButton(
                            text = when (state.buttonOption) {
                                ButtonOption.BUTTON_CHECK -> stringResource(id = R.string.check)
                                ButtonOption.BUTTON_NEXT -> stringResource(id = R.string.next)
                                ButtonOption.BUTTON_FINISH -> stringResource(id = R.string.finish)
                            },
                            isLoading = false,
                            onClick = {
                                onAction(WordAction.OnButtonCLick(state.buttonOption))
                            }
                        )
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFFD7D9CE)
                        )
                    }
                }
            }
    }
}

@Composable
private fun MainWordColumn(state: WordState) {
    Text(
        text = hideWord(
            sentence = state.word.sentence,
            wordEng = state.word.wordEng,
            buttonOption = state.buttonOption
        ),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF121211)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = when (state.buttonOption) {
            ButtonOption.BUTTON_CHECK -> state.word.wordPl
            ButtonOption.BUTTON_NEXT -> state.word.wordEng
            else -> ""
        },
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF121211)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier = Modifier
            .alpha(
                when (state.buttonOption) {
                    ButtonOption.BUTTON_CHECK -> 0f
                    ButtonOption.BUTTON_NEXT -> 1f
                    else -> 0f
                }
            )
    ) {
        Text(
            text = if (state.isCorrect) {
                stringResource(R.string.good)
            } else {
                stringResource(R.string.bad)
            },
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = if (state.isCorrect) {
                Color(0xFF0C7527)
            } else {
                Color(0xFFBE2020)
            },
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (state.isCorrect) {
                        Color(0xFF0C7527)
                    } else {
                        Color(0xFFBE2020)
                    },
                    shape = RoundedCornerShape(100f)
                )
                .padding(
                    vertical = 4.dp,
                    horizontal = 16.dp
                )
        )
    }
    JwTextField(
        state = state.answer,
        endIcon = null,
        hint = stringResource(R.string.answer),
        title = null,
        modifier = Modifier
            .alpha(
                when (state.buttonOption) {
                    ButtonOption.BUTTON_CHECK -> 1f
                    ButtonOption.BUTTON_NEXT -> 0f
                    else -> 0f
                }
            )
    )
}

@Composable
private fun SummaryColumn(state: WordState) {
    val average = remember {
        state.perfectGuesses.toFloat() / state.wordsNumber.toFloat()
    }

    Text(
        text = stringResource(R.string.summary),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Color(0xFF121211),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(R.string.you_have_repeated_words, state.wordsNumber),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFF5E5E5E)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = stringResource(
            R.string.you_have_guessed_of_perfectly,
            state.perfectGuesses,
            state.wordsNumber
        ),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFF5E5E5E)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = when (average) {
            in 0f..<0.5f -> {
                stringResource(R.string.below_average) }
            0.5f -> {
                stringResource(R.string.average) }
            else -> {
                stringResource(R.string.above_average) }
        },
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Medium,
        color = when (average) {
            in 0f..<0.5f -> {
                Color(0xFFBE2020) }
            0.5f -> {
                Color(0xFFA9791B) }
            else -> {
                Color(0xFF0C7527) }
        },
        modifier = Modifier
            .border(
                width = 1.dp,
                color = when (average) {
                    in 0f..<0.5f -> {
                        Color(0xFFBE2020) }
                    0.5f -> {
                        Color(0xFFA9791B) }
                    else -> {
                        Color(0xFF0C7527) }
                },
                shape = RoundedCornerShape(100f)
            )
            .padding(
                vertical = 4.dp,
                horizontal = 16.dp
            )
    )
}

@Preview
@Composable
private fun WordScreenPreview() {
    JustWords2Theme {
        WordScreen(
            state = WordState(
                words = listOf(
                    WordGuessable(
                        sentence = "The electricity company will send an employee to read your meter",
                        wordPl = "pracownik",
                        wordEng = "employee"
                    )
                ),
                book = Book(
                    name = "",
                    color = Color(0xFFD2614F).toArgb(),
                    bookId = ""
                ),
                buttonOption = ButtonOption.BUTTON_CHECK
            ),
            onAction = {}
        )
    }
}