package com.willaapps.user.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.ActionButton
import com.willaapps.core.presentation.designsystem.components.OutlinedActionButton
import com.willaapps.user.presentation.R

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(100f))
                .background(Color(0xFFD7D9CE))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.are_you_sure),
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Color(0xFF121211)
            )
            Text(
                text = stringResource(R.string.you_are_trying_to_logout_all_data_that_wasn_t_sync_with_internet_can_be_lost),
                fontSize = 16.sp,
                color = Color(0xFF5E5E5E)
            )
            Row {
                ActionButton(
                    text = stringResource(id = R.string.logout),
                    isLoading = false,
                    onClick = onConfirm,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedActionButton(
                    text = "Cancel",
                    isLoading = false,
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun LogoutDialogPreview() {
    JustWords2Theme {
        LogoutDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}