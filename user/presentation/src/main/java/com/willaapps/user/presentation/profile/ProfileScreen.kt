package com.willaapps.user.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.LevelIcon
import com.willaapps.core.presentation.designsystem.StreakIcon
import com.willaapps.core.presentation.designsystem.TrophyIcon
import com.willaapps.core.presentation.designsystem.UserIcon
import com.willaapps.core.presentation.designsystem.components.ActionButton
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.JwToolbar
import com.willaapps.core.presentation.designsystem.components.OutlinedActionButton
import com.willaapps.user.presentation.R
import com.willaapps.user.presentation.profile.components.SummaryItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreenRoot(
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    ProfileScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                ProfileAction.OnBackClick -> onBackClick()
                ProfileAction.OnEditProfileClick -> onEditProfileClick()
                ProfileAction.OnLogoutClick -> onLogoutClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
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
                text = stringResource(R.string.profile),
                onBackClick = {
                    onAction(ProfileAction.OnBackClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100f))
                    .background(Color(0xFFD7D9CE))
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = UserIcon,
                        contentDescription = null,
                        tint = Color(0xFF121211),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = state.username,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF121211)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.games_played, state.gamesCompleted),
                            fontSize = 16.sp,
                            color = Color(0xFF5E5E5E)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ActionButton(
                        text = stringResource(R.string.edit_profile),
                        isLoading = false,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onAction(ProfileAction.OnEditProfileClick)
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedActionButton(
                        text = stringResource(R.string.logout),
                        isLoading = false,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onAction(ProfileAction.OnLogoutClick)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100f))
                    .background(Color(0xFFD7D9CE))
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.summary),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF121211)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(100f))
                        .height(IntrinsicSize.Min)
                        .background(Color(0xFF119DA4))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SummaryItem(
                        icon = StreakIcon,
                        name = stringResource(R.string.daily_streak),
                        value = state.dailyStreak.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        color = Color(0xFFD7D9CE)
                    )
                    SummaryItem(
                        icon = TrophyIcon,
                        name = stringResource(R.string.best_streak),
                        value = state.bestStreak.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    VerticalDivider(
                        color = Color(0xFFD7D9CE)
                    )
                    SummaryItem(
                        icon = LevelIcon,
                        name = stringResource(R.string.level),
                        value = state.level,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(100f))
                    .background(Color(0xFFD7D9CE))
                    .padding(16.dp)
            ) {
                // TODO - history module
                // TODO - stats module
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    JustWords2Theme {
        ProfileScreen(
            state = ProfileState(
                username = "JohnDoe87",
                gamesCompleted = 123,
                dailyStreak = 7,
                bestStreak = 7,
                level = "Advanced"
            ),
            onAction = {}
        )
    }
}