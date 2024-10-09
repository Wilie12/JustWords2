package com.willaapps.core.data.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequest(
    val userInfo: UserInfoSerializable
)
