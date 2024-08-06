package com.willaapps.word.presentation.set_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.willaapps.core.domain.word.Book
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import com.willaapps.core.domain.word.WordSet
import com.willaapps.word.presentation.set_list.components.SetItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetListScreenRoot(
    onBackClick: () -> Unit,
    onSelectedGroupClick: (
        bookId: String,
        bookColor: Int,
        setId: String,
        groupNumber: Int
    ) -> Unit,
    viewModel: SetListViewModel = koinViewModel()
) {
    SetListScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                SetListAction.OnBackClick -> onBackClick()
                is SetListAction.OnGroupClick -> onSelectedGroupClick(
                    viewModel.state.book.bookId,
                    viewModel.state.book.color,
                    action.selectedSetId,
                    action.selectedGroup
                )
            }
        }
    )
}

@Composable
fun SetListScreen(
    state: SetListState,
    onAction: (SetListAction) -> Unit
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
                text = state.book.name,
                onBackClick = {
                    onAction(SetListAction.OnBackClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(state.sets) { set ->
                    SetItem(
                        set = set,
                        bookColor = Color(state.book.color),
                        onSelectedGroupClick = { setId, groupNumber ->
                            onAction(
                                SetListAction.OnGroupClick(
                                    selectedSetId = setId,
                                    selectedGroup = groupNumber
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SetListScreenPreview() {
    JustWords2Theme {
        SetListScreen(
            state = SetListState(
                book = Book(
                    name = "Work & Career",
                    bookId = "",
                    color = Color(0xFFD2614F).toArgb()
                ),
                sets = listOf(
                    WordSet(
                        name = "General vocabulary",
                        bookId = "",
                        numberOfGroups = 4,
                        id = ""
                    ),
                    WordSet(
                        name = "Application",
                        bookId = "",
                        numberOfGroups = 4,
                        id = ""
                    ),
                    WordSet(
                        name = "Unemployment",
                        bookId = "",
                        numberOfGroups = 4,
                        id = ""
                    ),
                    WordSet(
                        name = "Working hours",
                        bookId = "",
                        numberOfGroups = 4,
                        id = ""
                    )
                )
            ),
            onAction = {}
        )
    }
}