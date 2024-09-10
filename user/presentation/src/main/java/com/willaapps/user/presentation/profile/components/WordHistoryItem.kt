package com.willaapps.user.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun WordHistoryItem(
    wordHistory: WordHistory,
    index: Int,
    modifier: Modifier = Modifier
) {
    val dateTimeInLocalTime = wordHistory.dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - HH:mm")
        .format(dateTimeInLocalTime)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100f))
            .background(Color(wordHistory.bookColor))
            .padding(16.dp)
    ) {
        Text(
            text = index.toString(),
            color = Color(0xFFD7D9CE),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(Color(0xFF121211))
                .padding(
                    vertical = 4.dp,
                    horizontal = 8.dp
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = wordHistory.bookName,
                    color = Color(0xFFD7D9CE),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${wordHistory.perfectGuessed}/${wordHistory.wordListSize}",
                    color = Color(0xFFD7D9CE),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(100f))
                        .background(Color(0xFF121211))
                        .padding(
                            vertical = 4.dp,
                            horizontal = 8.dp
                        )
                )
            }
            Text(
                text = "${wordHistory.setName} - Group ${wordHistory.groupNumber}",
                color = Color(0xFFBDBFB5),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = formattedDateTime,
                color = Color(0xFFBDBFB5),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun WordHistoryItemPreview() {
    JustWords2Theme {
        WordHistoryItem(
            wordHistory = WordHistory(
                bookName = "Test Book 2",
                bookColor = -411335449,
                setName = "Word set 2 from book 2",
                groupNumber = 1,
                dateTimeUtc = Instant.parse(
                    "2024-09-09T16:42:05.937Z"
                ).atZone(ZoneId.of("UTC")),
                perfectGuessed = 12,
                wordListSize = 25,
                id = ""
            ),
            index = 21
        )
    }
}