package com.willaapps.shop.domain

data class ShopWordSet(
    val name: String,
    val bookId: String,
    val bookColor: Int,
    val numberOfGroups: Int,
    val id: String,
    val isDownloaded: Boolean
)
