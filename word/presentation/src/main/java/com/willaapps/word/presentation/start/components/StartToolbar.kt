package com.willaapps.word.presentation.start.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.UserIcon
import com.willaapps.word.presentation.R

@Composable
fun StartToolbar(
    text: String,
    icon: ImageVector,
    onIconClick: (() -> Unit),
    modifier: Modifier = Modifier,
    textColor: Color = Color(0xFFD7D9CE),
    fontWeight: FontWeight = FontWeight.Medium
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
            color = textColor,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.profile),
            tint = textColor,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable {
                    onIconClick()
                }
        )
    }
}

@Preview
@Composable
private fun StartToolbarPreview() {
    JustWords2Theme {
        StartToolbar(
            text = "Welcome, User",
            icon = UserIcon,
            onIconClick = { }
        )
    }
}