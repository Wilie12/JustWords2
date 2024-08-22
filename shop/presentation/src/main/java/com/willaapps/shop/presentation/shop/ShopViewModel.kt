package com.willaapps.shop.presentation.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ShopViewModel: ViewModel() {

    var state by mutableStateOf(ShopState())
}