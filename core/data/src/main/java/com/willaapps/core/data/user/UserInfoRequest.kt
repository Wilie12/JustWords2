package com.willaapps.core.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequest(
    val userInfo: UserInfoSerializable
)
