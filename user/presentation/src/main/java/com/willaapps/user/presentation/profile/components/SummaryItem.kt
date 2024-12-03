package com.willaapps.user.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun SummaryItem(
    icon: ImageVector,
    name: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFD7D9CE)
        )
        Text(
            text = name,
            color = Color(0xFFD7D9CE),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        Text(
            text = value,
            color = Color(0xFFD7D9CE),
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}