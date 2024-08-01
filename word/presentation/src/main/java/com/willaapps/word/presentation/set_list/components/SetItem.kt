package com.willaapps.word.presentation.set_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.domain.word.WordSet

@Composable
fun SetItem(
    set: WordSet,
    bookColor: Color,
    onSelectedGroupClick: (setId: String, groupNumber: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100f))
            .background(Color(0xFFD7D9CE))
            .padding(16.dp)
    ) {
        Text(
            text = set.name,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            color = Color(0xFF121211)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(set.numberOfGroups) { index ->
                SetGroupItem(
                    number = index + 1,
                    color = bookColor,
                    onSelectedGroupClick = { groupNumber ->
                        onSelectedGroupClick(set.id, groupNumber)
                    }
                )
            }
        }
    }
}

@Composable
private fun SetGroupItem(
    number: Int,
    color: Color,
    onSelectedGroupClick: (groupNumber: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(color = color)
            .clickable {
                onSelectedGroupClick(number)
            }
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
    ) {
        Text(
            text = number.toString(),
            fontSize = 24.sp,
            color = Color(0xFFD7D9CE)
        )
        Text(
            text = "Group",
            fontSize = 16.sp,
            color = Color(0xFFD7D9CE)
        )
    }
}

@Preview
@Composable
private fun SetItemPreview() {
    JustWords2Theme {
        SetItem(
            set = WordSet(
                name = "General vocabulary",
                bookId = "",
                numberOfGroups = 4,
                id = ""
            ),
            bookColor = Color(0xFFD2614F),
            onSelectedGroupClick = { _, _ -> }
        )
    }
}