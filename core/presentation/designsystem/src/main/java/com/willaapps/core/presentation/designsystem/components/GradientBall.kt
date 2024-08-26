package com.willaapps.core.presentation.designsystem.components

import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GradientBall(
    gradientColor: Color,
    modifier: Modifier = Modifier,
    offsetY: Float = 0f,
    reverse: Boolean = false
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) {
        configuration.screenWidthDp.dp.toPx()
    }
    val screenHeightPx = with(density) {
        configuration.screenHeightDp.dp.toPx()
    }

    val smallDimension = minOf(
        configuration.screenWidthDp.dp,
        configuration.screenHeightDp.dp
    )

    val smallDimensionPx = with(density) {
        smallDimension.roundToPx()
    }

    val isAtLeastAndroid12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val animatedColor by animateColorAsState(
        targetValue = if (animationPlayed) gradientColor else Color.Black,
        animationSpec = tween(
            durationMillis = 800,
            easing = LinearEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(modifier = modifier
        .fillMaxSize()
        .then(
            if (isAtLeastAndroid12) {
                Modifier.blur(smallDimension / 3f)
            } else Modifier
        )
        .background(
            brush = Brush.radialGradient(
                colors = listOf(
                    if (isAtLeastAndroid12) animatedColor else animatedColor.copy(alpha = 0.3f),
                    Color.Transparent
                ),
                center = Offset(
                    x = screenWidthPx / 2f,
                    y = if (reverse) {
                        screenHeightPx - offsetY
                    } else {
                        offsetY + offsetY
                    }
                ),
                radius = smallDimensionPx / 2f
            )
        )
    )
}

@Preview
@Composable
private fun GradientBallPreview() {
    GradientBall(
        gradientColor = Color(0xFF119DA4),
        offsetY = 100f
    )
}