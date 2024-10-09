package com.willaapps.core.data.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val userInfo: UserInfoSerializable
)
