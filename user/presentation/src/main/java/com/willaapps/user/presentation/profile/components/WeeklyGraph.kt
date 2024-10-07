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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
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
import com.willaapps.user.domain.profile.WeeklyGraphItem
import com.willaapps.user.presentation.R
import com.willaapps.user.presentation.profile.util.calculateValuesBetween

@Composable
fun WeeklyGraph(
    weeklyPlays: List<WeeklyGraphItem>,
    today: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.weekly),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF121211)
        )
        Spacer(modifier = Modifier.height(8.dp))
        BoxWithConstraints {
            val boxScope = this
            val maxPlayed = remember {
                weeklyPlays.maxOf { it.timesPlayed }
            }
            val timesPlayedGraphNumbers = remember {
                calculateValuesBetween(maxPlayed).sortedDescending()
            }
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                val weeklyPlaysSorted = remember {
                    weeklyPlays.sortedBy { it.dayOfWeek }
                }
                Column(modifier = Modifier.weight(1f)) {
                    Canvas(
                        modifier = Modifier
                            .size(
                                width = boxScope.maxWidth,
                                height = boxScope.maxWidth / 2
                            )
                            .clip(RoundedCornerShape(100f))
                            .background(Color(0xFFBDBFB5))
                    ) {
                        weeklyPlaysSorted.forEach {
                            val cornerRadius = CornerRadius(30f, 30f)
                            val path = Path().apply {
                                addRoundRect(
                                    RoundRect(
                                        rect = Rect(
                                            offset = Offset(
                                                x = ((size.width * it.dayOfWeek) / 7) + 16,
                                                y = size.height - ((((size.height * 2) / 3) * it.timesPlayed) / maxPlayed)
                                            ),
                                            size = Size(
                                                width = (size.width / 7) - 32,
                                                height = (((size.height * 2) / 3) * it.timesPlayed) / maxPlayed + 10
                                            )
                                        ),
                                        topLeft = cornerRadius,
                                        topRight = cornerRadius
                                    )
                                )
                            }
                            drawPath(
                                path, brush = Brush.verticalGradient(
                                    colors = if (it.dayOfWeek == today) {
                                        listOf(
                                            Color(0xFF119DA4),
                                            Color.Transparent
                                        )
                                    } else {
                                        listOf(
                                            Color(0xFF5E5E5E),
                                            Color.Transparent
                                        )
                                    }
                                )
                            )
                            drawPath(
                                path,
                                color = if (it.dayOfWeek == today) {
                                    Color(0xFF119DA4)
                                } else {
                                    Color(0xFF5E5E5E)

                                },
                                style = Stroke(
                                    width = 10f,

                                    )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val listOfDays = remember {
                            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
                        }
                        listOfDays.forEach {
                            Text(
                                text = it,
                                fontSize = 14.sp,
                                color = Color(0xFF121211),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
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
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun WeeklyGraphPreview() {
    JustWords2Theme {
        WeeklyGraph(
            weeklyPlays = listOf(
                WeeklyGraphItem(
                    dayOfWeek = 0,
                    timesPlayed = 6
                ),
                WeeklyGraphItem(
                    dayOfWeek = 1,
                    timesPlayed = 5
                ),
                WeeklyGraphItem(
                    dayOfWeek = 2,
                    timesPlayed = 2
                ),
                WeeklyGraphItem(
                    dayOfWeek = 3,
                    timesPlayed = 6
                ),
                WeeklyGraphItem(
                    dayOfWeek = 4,
                    timesPlayed = 4
                ),
                WeeklyGraphItem(
                    dayOfWeek = 5,
                    timesPlayed = 2
                )
            ),
            today = 5
        )
    }
}