package com.willaapps.user.presentation.profile.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.core.presentation.designsystem.UnCheckCircleIcon
import com.willaapps.user.presentation.R
import com.willaapps.user.presentation.profile.util.DailyGraphItem
import com.willaapps.user.presentation.profile.util.calculateValuesBetween

@Composable
fun DailyGraph(
    todayPlays: List<DailyGraphItem>,
    yesterdayPlays: List<DailyGraphItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.daily),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF121211)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = UnCheckCircleIcon,
                    contentDescription = null,
                    tint = Color(0xFF119DA4),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(R.string.today),
                    fontSize = 14.sp,
                    color = Color(0xFF5E5E5E)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = UnCheckCircleIcon,
                    contentDescription = null,
                    tint = Color(0xFF5E5E5E),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(R.string.yesterday),
                    fontSize = 14.sp,
                    color = Color(0xFF5E5E5E)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        BoxWithConstraints {
            val boxScope = this
            val maxPlayed = remember {
                val totalTimesPlayed = (todayPlays.map { it.timesPlayed }
                        + yesterdayPlays.map { it.timesPlayed })

                totalTimesPlayed.max()
            }
            val timesPlayedGraphNumbers = remember {
                calculateValuesBetween(maxPlayed).sortedDescending()
            }
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                val todayPlayedSorted = remember { todayPlays.sortedBy { it.hour } }
                val yesterdayPlayedSorted = remember { yesterdayPlays.sortedBy { it.hour } }

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(
                                width = boxScope.maxWidth,
                                height = boxScope.maxWidth / 2
                            )
                            .clip(RoundedCornerShape(100f))
                            .background(Color(0xFFBDBFB5))
                    ) {
                        // TODO - use cubic to smooth curves
                        val pathToday = Path().apply {
                            moveTo(0f, size.height)
                            todayPlayedSorted.forEach {
                                val firstX = (size.width * it.hour) / 25
                                val firstY =
                                    size.height - ((((size.height * 2) / 3) * it.timesPlayed) / maxPlayed)
                                lineTo(firstX, firstY)
                            }
                            lineTo(size.width, size.height)
                        }
                        val pathYesterday = Path().apply {
                            moveTo(0f, size.height)
                            yesterdayPlayedSorted.forEach {
                                val firstX = (size.width * it.hour) / 25
                                val firstY =
                                    size.height - ((((size.height * 2) / 3) * it.timesPlayed) / maxPlayed)
                                lineTo(firstX, firstY)
                            }
                            lineTo(size.width, size.height)
                        }
                        drawPath(pathToday, color = Color(0xFF119DA4), style = Stroke(width = 5f))
                        drawPath(pathYesterday, color = Color(0xFF5E5E5E), style = Stroke(width = 5f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val listOfHours = remember {
                            listOf(0, 4, 8, 12, 16, 20, 24)
                        }
                        listOfHours.forEach {
                            Text(
                                text = it.toString(),
                                fontSize = 14.sp,
                                color = Color(0xFF121211),
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier
                        .height(boxScope.maxWidth / 2)
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    timesPlayedGraphNumbers.forEach {
                        Text(
                            text = it.toString(),
                            fontSize = 14.sp,
                            color = Color(0xFF121211),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DailyGraphPreview() {
    JustWords2Theme {
        DailyGraph(
            todayPlays = listOf(
                DailyGraphItem(
                    hour = 8,
                    timesPlayed = 4
                ),
                DailyGraphItem(
                    hour = 12,
                    timesPlayed = 3
                ),
                DailyGraphItem(
                    hour = 16,
                    timesPlayed = 1
                ),
                DailyGraphItem(
                    hour = 20,
                    timesPlayed = 7
                )
            ),
            yesterdayPlays = listOf(
                DailyGraphItem(
                    hour = 2,
                    timesPlayed = 4
                ),
                DailyGraphItem(
                    hour = 7,
                    timesPlayed = 3
                ),
                DailyGraphItem(
                    hour = 15,
                    timesPlayed = 5
                ),
                DailyGraphItem(
                    hour = 20,
                    timesPlayed = 1
                )
            )
        )
    }
}