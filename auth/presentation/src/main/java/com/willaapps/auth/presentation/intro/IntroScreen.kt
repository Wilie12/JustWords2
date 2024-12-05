package com.willaapps.auth.presentation.intro

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.auth.presentation.R
import com.willaapps.auth.presentation.intro.components.BackgroundText
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.LogoIcon
import com.willaapps.core.presentation.designsystem.components.ActionButton
import com.willaapps.core.presentation.designsystem.components.GradientBall
import com.willaapps.core.presentation.designsystem.components.OutlinedActionButton

@Composable
fun IntroScreenRoot(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    IntroScreen(
        onAction = { action ->
            when (action) {
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSignUpClick -> onSignUpClick()
            }
        }
    )
}

@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animated by infiniteTransition.animateFloat(
        initialValue = -140f,
        targetValue = -300f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 30000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "1"
    )
    val reverseAnimated by infiniteTransition.animateFloat(
        initialValue = -300f,
        targetValue = -140f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 30000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "2"
    )


    val backgroundTexts = listOf(
        stringResource(id = R.string.bg_text_1),
        stringResource(id = R.string.bg_text_2),
        stringResource(id = R.string.bg_text_3),
        stringResource(id = R.string.bg_text_4),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF040404))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFF040404))
        ) {
            GradientBall(
                gradientColor = Color(0xFF119DA4),
                reverse = true,
                offsetY = 500f
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .alpha(0.15f)
                            .rotate(45f)
                    ) {
                        var index = 1

                        for (i in 1..4) {
                            backgroundTexts.forEach { text ->
                                BackgroundText(
                                    text = text,
                                    modifier = Modifier
                                        .offset(
                                            x = if (index % 2 == 0) {
                                                (animated).dp
                                            } else {
                                                (reverseAnimated).dp
                                            },
                                            y = ((50 * index) - 100).dp
                                        )

                                )
                                index++
                            }
                        }
                    }
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = "Logo",
                        tint = Color(0xFFD7D9CE)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 100f,
                                topEnd = 100f,
                            )
                        )
                        .background(color = Color(0xFFD7D9CE))
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.start_learning_now),
                        fontSize = 24.sp,
                        color = Color(0xFF121211),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.take_your_first_step_towards_mastering_a_new_language_today),
                        fontSize = 16.sp,
                        color = Color(0xFF5E5E5E),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ActionButton(
                        text = stringResource(id = R.string.sign_in),
                        isLoading = false,
                        onClick = {
                            onAction(IntroAction.OnSignInClick)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedActionButton(
                        text = stringResource(id = R.string.sign_up),
                        isLoading = false,
                        onClick = {
                            onAction(IntroAction.OnSignUpClick)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    JustWords2Theme {
        IntroScreen(
            onAction = {}
        )
    }
}