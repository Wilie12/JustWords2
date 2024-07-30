package com.willaapps.word.presentation.start.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.word.presentation.R

@Composable
fun DailyGoalBox(
    dailyGoalAim: Int,
    dailyGoalCurrent: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100f))
            .background(color = Color(0xFFD7D9CE))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(3f)) {
            Text(
                text = stringResource(R.string.daily_goal),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF121211)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.practice_more_to_finish_daily_goal),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF5E5E5E)
            )
        }
        Spacer(modifier = Modifier.width(32.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(1.3f)
                .aspectRatio(1f)
                .padding(4.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .matchParentSize()
            ) {
                drawOval(
                    color = Color(0xFFBDBFB5),
                    style = Stroke(
                        width = 50f
                    )
                )
                drawArc(
                    color = Color(0xFF119DA4),
                    startAngle = 90f,
                    sweepAngle = ((dailyGoalCurrent.toFloat()/dailyGoalAim.toFloat()) * 360f),
                    useCenter = false,
                    style = Stroke(
                        width = 50f,
                        cap = StrokeCap.Round
                    )
                )
            }
            Text(
                text = "$dailyGoalCurrent/$dailyGoalAim",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF1D1B20)
            )
        }
    }
}

@Preview
@Composable
private fun DailyGoalBoxPreview() {
    JustWords2Theme {
        DailyGoalBox(
            dailyGoalAim = 4,
            dailyGoalCurrent = 2
        )
    }
}