package com.willaapps.word.presentation.start.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.ForwardIcon
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.word.domain.PreviousWord
import com.willaapps.word.presentation.R

@Composable
fun PreviousBox(
    previousWord: PreviousWord,
    onPreviousWordClick: (
        bookId: String,
        bookColor: Int,
        setId: String,
        groupNumber: Int
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100f))
            .background(color = Color(0xFFD7D9CE))
            .padding(16.dp)
    ) {
        Text(
            text = "Previous",
            fontSize = 20.sp,
            color = Color(0xFF121211)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(100f))
                .background(color = Color(previousWord.bookColor))
                .clickable {
                    onPreviousWordClick(
                        previousWord.bookId,
                        previousWord.bookColor,
                        previousWord.setId,
                        previousWord.groupNumber
                    )
                }
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = previousWord.bookName,
                    fontSize = 16.sp,
                    color = Color(0xFFD7D9CE)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${previousWord.setName} - Group ${previousWord.groupNumber}",
                    fontSize = 16.sp,
                    color = Color(0xFFBDBFB5)
                )
            }
            Icon(
                imageVector = ForwardIcon,
                contentDescription = stringResource(R.string.open_previous_words),
                tint = Color(0xFFD7D9CE),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviousBoxPreview() {
    JustWords2Theme {
        PreviousBox(
            previousWord = PreviousWord(
                bookId = "1",
                bookColor = Color(0xFF64A062).toArgb(),
                bookName = "Food & Cooking",
                setId = "1",
                setName = "Ingredients",
                groupNumber = 2
            ),
            onPreviousWordClick = {_, _, _, _ -> }
        )
    }
}