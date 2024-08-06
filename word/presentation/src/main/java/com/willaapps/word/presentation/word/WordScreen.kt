@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.word.presentation.word

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.ActionButton
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwTextField
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import com.willaapps.word.presentation.R
import com.willaapps.word.presentation.word.util.hideWord
import org.koin.androidx.compose.koinViewModel

@Composable
fun WordScreenRoot(
    onBackClick: () -> Unit,
    onFinishClick: (
        perfectGuesses: Int,
        wordsNumber: Int,
        bookColor: Int
    ) -> Unit,
    viewModel: WordViewModel = koinViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.show()

    WordScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                WordAction.OnBackClick -> onBackClick()
                is WordAction.OnButtonCLick -> {
                    when (action.buttonOption) {
                        ButtonOption.BUTTON_FINISH -> TODO()
                        else -> Unit
                    }
                }
            }
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
            gradientColor = Color(state.bookColor),
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100f))
                    .background(Color(0xFFD7D9CE))
                    .padding(16.dp)
            ) {
                Text(
                    text = hideWord(
                        sentence = state.sentence,
                        wordEng = state.wordEng,
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
                        ButtonOption.BUTTON_CHECK -> state.wordPl
                        ButtonOption.BUTTON_NEXT -> state.wordEng
                        ButtonOption.BUTTON_FINISH -> state.wordEng
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
                                ButtonOption.BUTTON_FINISH -> 1f
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
                                ButtonOption.BUTTON_FINISH -> 0f
                            }
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
                ActionButton(
                    text = when (state.buttonOption) {
                        ButtonOption.BUTTON_CHECK -> stringResource(id = R.string.check)
                        ButtonOption.BUTTON_NEXT -> stringResource(id = R.string.next)
                        ButtonOption.BUTTON_FINISH -> stringResource(id = R.string.finish)
                    },
                    isLoading = false,
                    onClick = {
                        // TODO
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun WordScreenPreview() {
    JustWords2Theme {
        WordScreen(
            state = WordState(
                bookColor = Color(0xFFD2614F).toArgb(),
                sentence = "The electricity company will send an employee to read your meter",
                wordPl = "pracownik",
                wordEng = "employee",
                buttonOption = ButtonOption.BUTTON_CHECK
            ),
            onAction = {}
        )
    }
}