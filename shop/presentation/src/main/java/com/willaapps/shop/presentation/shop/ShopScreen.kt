package com.willaapps.shop.presentation.shop

import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.willaapps.core.domain.word.Book
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import com.willaapps.core.presentation.ui.ObserveAsEvents
import com.willaapps.shop.domain.ShopBook
import com.willaapps.shop.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShopScreenRoot(
    onBackClick: () -> Unit,
    onShopBookClick: (bookId: String) -> Unit,
    viewModel: ShopViewModel = koinViewModel()
) {
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is ShopEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
                onBackClick()
            }
        }
    }
    ShopScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ShopAction.OnBackClick -> onBackClick()
                is ShopAction.OnBookClick -> {
                    onShopBookClick(action.bookId)
                }
            }
        }
    )
}

@Composable
private fun ShopScreen(
    state: ShopState,
    onAction: (ShopAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GradientBall(
            gradientColor = Color(0xFF119DA4),
            offsetY = 200f
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            JwToolbar(
                text = stringResource(R.string.shop),
                onBackClick = {
                    onAction(ShopAction.OnBackClick)
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
                    items(state.shopBooks) { shopBook ->
                        ShopBookItem(
                            shopBook = shopBook,
                            onShopBookClick = {
                                onAction(ShopAction.OnBookClick(shopBook.book.bookId))
                            }
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
private fun ShopScreenPreview() {
    JustWords2Theme {
        ShopScreen(
            state = ShopState(
                shopBooks = listOf(
                    ShopBook(
                        book = Book(
                            name = "Work & Career",
                            bookId = "1",
                            color = Color(0xFFD2614F).toArgb()
                        ),
                        setNames = listOf(
                            "General vocabulary",
                            "Application",
                            "Unemployment",
                            "Working hours",
                            "Workplace"
                        )
                    ),
                    ShopBook(
                        book = Book(
                            name = "Health Care",
                            bookId = "2",
                            color = Color(0xFF589BCC).toArgb()
                        ),
                        setNames = listOf(
                            "General vocabulary",
                            "Application",
                            "Unemployment",
                            "Working hours",
                            "Workplace"
                        )
                    ),
                    ShopBook(
                        book = Book(
                            name = "Food & Cooking",
                            bookId = "3",
                            color = Color(0xFF64A062).toArgb()
                        ),
                        setNames = listOf(
                            "General vocabulary",
                            "Application",
                            "Unemployment",
                            "Working hours",
                            "Workplace"
                        )
                    ),
                    ShopBook(
                        book = Book(
                            name = "Personal Information",
                            bookId = "4",
                            color = Color(0xFF964EA1).toArgb()
                        ),
                        setNames = listOf(
                            "General vocabulary",
                            "Application",
                            "Unemployment",
                            "Working hours",
                            "Workplace"
                        )
                    ),
                    ShopBook(
                        book = Book(
                            name = "Places to visit",
                            bookId = "5",
                            color = Color(0xFFD89142).toArgb()
                        ),
                        setNames = listOf(
                            "General vocabulary",
                            "Application",
                            "Unemployment",
                            "Working hours",
                            "Workplace"
                        )
                    )
                )
            ),
            onAction = {}
        )
    }
}