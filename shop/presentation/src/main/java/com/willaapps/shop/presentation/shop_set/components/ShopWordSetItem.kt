package com.willaapps.shop.presentation.shop_set.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.willaapps.core.presentation.designsystem.AddIcon
import com.willaapps.core.presentation.designsystem.CheckIcon
import com.willaapps.core.presentation.designsystem.JustWords2Theme
import com.willaapps.shop.domain.model.ShopWordSet

@Composable
fun ShopWordSetItem(
    shopWordSet: ShopWordSet,
    onShopWordSetClick: (setId: String) -> Unit,
    bookColor: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(100f))
            .background(
                color = if (shopWordSet.isDownloaded) {
                    Color(0xFF5E5E5E).copy(0.7f)
                } else {
                    Color(0xFFD7D9CE)
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = shopWordSet.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF121211)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${shopWordSet.numberOfGroups} groups",
                fontSize = 16.sp,
                color = if (shopWordSet.isDownloaded) {
                    Color(0xFF121211).copy(0.7f)
                } else {
                    Color(0xFF5E5E5E)

                },
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = if (shopWordSet.isDownloaded) {
                CheckIcon
            } else {
                AddIcon
            },
            contentDescription = "Download set ${shopWordSet.name}",
            tint = if (
                shopWordSet.isDownloaded
            ) {
                Color(0xFF5E5E5E)
            } else {
                Color(0xFFD7D9CE)
            },
            modifier = Modifier
                .clip(RoundedCornerShape(100f))
                .background(
                    color = if (shopWordSet.isDownloaded) {
                        Color(0xFF121211).copy(0.7f)
                    } else {
                        Color(bookColor)
                    }
                )
                .then(
                    if (shopWordSet.isDownloaded) {
                        Modifier
                    } else {
                        Modifier.clickable {
                            onShopWordSetClick(shopWordSet.id)
                        }
                    }
                )
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
private fun ShopWordSetItemPreview() {
    JustWords2Theme {
        ShopWordSetItem(
            shopWordSet = ShopWordSet(
                name = "General vocabulary",
                bookId = "1",
                numberOfGroups = 4,
                id = "1",
                isDownloaded = false
            ),
            bookColor = Color(0xFFD2614F).toArgb(),
            onShopWordSetClick = {}
        )
    }
}