package com.willaapps.word.presentation.start.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun BookItem(
    book: Book,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100f))
            .background(color = Color(book.color))
            .clickable {
                onBookClick(book.bookId)
            }
            .padding(16.dp)
    ) {
        Text(
            text = book.name,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Color(0xFFD7D9CE)
        )
        Icon(
            imageVector = ForwardIcon,
            contentDescription = "Open book ${book.name}",
            tint = Color(0xFFD7D9CE),
            modifier = Modifier.size(32.dp)
        )
    }
}

@Preview
@Composable
private fun BookItemPreview() {
    JustWords2Theme {
        BookItem(
            book = Book(
                name = "Work & Career",
                bookId = "",
                color = Color(0xFFD2614F).toArgb()
            ),
            onBookClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}