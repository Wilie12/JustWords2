package com.willaapps.core.presentation.designsystem.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
                    if (isAtLeastAndroid12) gradientColor else gradientColor.copy(alpha = 0.3f),
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