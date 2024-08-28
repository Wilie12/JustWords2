@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.shop.presentation.shop_set

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.willaapps.core.domain.word.Book
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import com.willaapps.core.presentation.ui.ObserveAsEvents
import com.willaapps.shop.domain.ShopWordSet
import com.willaapps.shop.presentation.R
import com.willaapps.shop.presentation.shop_set.components.ShopWordSetItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShopSetScreenRoot(
    onBackClick: () -> Unit,
    viewModel: ShopSetViewModel = koinViewModel()
) {
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is ShopSetEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
                onBackClick()
            }

            ShopSetEvent.SuccessfullyDownloaded -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.successfully_downloaded_a_new_set),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    ShopSetScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ShopSetAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ShopSetScreen(
    state: ShopSetState,
    onAction: (ShopSetAction) -> Unit
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
                    onAction(ShopSetAction.OnBackClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (!state.isLoading) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(
                        items = state.shopWordSets.sortedBy { it.isDownloaded },
                        key = { it.id }
                    ) { shopWordSet ->
                        ShopWordSetItem(
                            shopWordSet = shopWordSet,
                            bookColor = state.book.color,
                            onShopWordSetClick = {
                                onAction(ShopSetAction.OnAddWordSetClick(it))
                            },
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFD7D9CE)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShopSetScreenPreview() {
    JustWords2Theme {
        ShopSetScreen(
            state = ShopSetState(
                shopWordSets = listOf(
                    ShopWordSet(
                        name = "General vocabulary",
                        bookId = "1",
                        numberOfGroups = 4,
                        id = "1",
                        isDownloaded = false
                    ),
                    ShopWordSet(
                        name = "Application",
                        bookId = "1",
                        numberOfGroups = 5,
                        id = "2",
                        isDownloaded = false
                    ),
                    ShopWordSet(
                        name = "Unemployment",
                        bookId = "1",
                        numberOfGroups = 5,
                        id = "3",
                        isDownloaded = true
                    )
                ),
                book = Book(
                    name = "Work & Career",
                    color = Color(0xFFD2614F).toArgb(),
                    bookId = "1"
                ),
            ),
            onAction = {}
        )
    }
}