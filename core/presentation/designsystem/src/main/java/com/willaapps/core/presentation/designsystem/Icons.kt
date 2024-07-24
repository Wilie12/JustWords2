package com.willaapps.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

val VisibleIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.visible)
val InvisibleIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.invisible)
val UserIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.user)
val ShopIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.shop)
val LogoIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.logo)
val BackIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.back)