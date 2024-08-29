@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.shop.presentation.shop.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.domain.word.Book
import com.willaapps.core.presentation.designsystem.ForwardIcon
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.shop.domain.model.ShopBook

@Composable
fun ShopBookItem(
    shopBook: ShopBook,
    onShopBookClick: (bookId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var setNamesFormatted = ""
    shopBook.setNames.forEach { name ->
        setNamesFormatted += "$name - "
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100f))
            .background(Color(0xFFD7D9CE))
            .clickable {
                onShopBookClick(shopBook.book.bookId)
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = shopBook.book.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF121211)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = setNamesFormatted,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                color = Color(shopBook.book.color).copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(
                        spacing = MarqueeSpacing(0.dp),
                        delayMillis = 1000
                    )
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = ForwardIcon,
            contentDescription = "Open shop book ${shopBook.book.name}",
            tint = Color(0xFFD7D9CE),
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(Color(shopBook.book.color))
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun ShopBookItemPreview() {
    JustWords2Theme {
        ShopBookItem(
            shopBook = ShopBook(
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
            onShopBookClick = {}
        )
    }
}