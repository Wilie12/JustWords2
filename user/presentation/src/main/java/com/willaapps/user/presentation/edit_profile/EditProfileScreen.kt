@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.user.presentation.edit_profile

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.willaapps.core.presentation.designsystem.CheckIcon
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.components.ActionButton
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwTextField
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import com.willaapps.core.presentation.designsystem.components.OutlinedActionButton
import com.willaapps.core.presentation.ui.ObserveAsEvents
import com.willaapps.user.presentation.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun EditProfileScreenRoot(
    onNavigateBack: () -> Unit,
    viewModel: EditProfileViewModel = koinViewModel()
) {
    val context = LocalContext.current

    ObserveAsEvents(flow = viewModel.eventChannel) {
        Toast.makeText(
            context,
            context.getString(R.string.profile_edited_successfully),
            Toast.LENGTH_LONG
        ).show()
        onNavigateBack()
    }

    EditProfileScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                EditProfileAction.OnCancelClick -> onNavigateBack()
                EditProfileAction.OnBackClick -> onNavigateBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}
@Composable
fun EditProfileScreen(
    state: EditProfileState,
    onAction: (EditProfileAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GradientBall(
            gradientColor = Color(0xFF119DA4),
            offsetY = 200f
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            JwToolbar(
                text = stringResource(id = R.string.edit_profile),
                onBackClick = {
                    onAction(EditProfileAction.OnBackClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(100f))
                    .background(Color(0xFFD7D9CE))
                    .padding(16.dp)
            ) {
                JwTextField(
                    state = state.username,
                    endIcon = if (state.isValidUsername) CheckIcon else null,
                    hint = stringResource(R.string.enter_new_username),
                    title = stringResource(R.string.username)
                )
                Spacer(modifier = Modifier.height(16.dp))
                JwTextField(
                    state = state.dailyGoal,
                    endIcon = if (state.isValidDailyGoal) CheckIcon else null,
                    hint = stringResource(R.string.enter_new_daily_goal),
                    title = stringResource(R.string.daily_goal),
                    keyboardType = KeyboardType.Number
                )
                Spacer(modifier = Modifier.weight(1f))
                ActionButton(
                    text = stringResource(R.string.save),
                    isLoading = state.isLoading,
                    enabled = state.canSave && !state.isLoading,
                    onClick = {
                        onAction(EditProfileAction.OnSaveClick)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedActionButton(
                    text = stringResource(R.string.cancel),
                    isLoading = state.isLoading,
                    onClick = {
                        onAction(EditProfileAction.OnCancelClick)
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    JustWords2Theme {
        EditProfileScreen(
            state = EditProfileState(),
            onAction = {}
        )
    }
}