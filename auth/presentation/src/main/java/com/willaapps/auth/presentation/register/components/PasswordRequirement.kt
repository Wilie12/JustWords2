package com.willaapps.auth.presentation.register.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.CheckCircleIcon
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.UnCheckCircleIcon

@Composable
fun PasswordRequirement(
    text: String,
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isValid) {
                CheckCircleIcon
            } else {
                UnCheckCircleIcon
            },
            contentDescription = null,
            tint = if (isValid) {
                Color(0xFF121211)
            } else {
                Color(0xFF5E5E5E)
            },
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = Color(0xFF5E5E5E),
            fontSize = 14.sp
        )
    }
}

@Preview
@Composable
private fun PasswordRequirementPreview() {
    JustWords2Theme {
        PasswordRequirement(
            text = "At least one number",
            isValid = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}