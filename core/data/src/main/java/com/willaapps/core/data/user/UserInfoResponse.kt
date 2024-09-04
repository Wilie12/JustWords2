package com.willaapps.core.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val userInfo: UserInfoSerializable
)
