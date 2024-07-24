package com.willaapps.auth.presentation.intro.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun BackgroundText(
    text: String,
    modifier: Modifier = Modifier
) {

    val wordList = text.split(" ")

    val style1 = SpanStyle(
        color = Color(0xFFD7D9CE)
    )
    val style2 = SpanStyle(
        color = Color(0xFF5E5E5E)
    )

    val annotatedText = buildAnnotatedString {
        wordList.forEachIndexed { index, word ->
            if (index % 2 == 0) {
                pushStyle(style1)
                append("$word ")
            } else {
                pushStyle(style2)
                append("$word ")
            }
        }
    }

    Text(
        text = annotatedText,
        fontSize = 48.sp,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Visible,
        modifier = modifier
            .fillMaxSize()
    )
}