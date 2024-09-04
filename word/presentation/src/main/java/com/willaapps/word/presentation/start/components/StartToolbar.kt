package com.willaapps.word.presentation.start.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.word.presentation.R

@Composable
fun StartToolbar(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color(0xFFD7D9CE),
    fontWeight: FontWeight = FontWeight.Medium,
    icon: ImageVector? = null,
    onIconClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = fontWeight,
            color = textColor
        )
        if (icon != null) {
            IconButton(onClick = {
                if (onIconClick != null) {
                    onIconClick()
                } else Unit
            }) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.profile),
                    tint = textColor,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}