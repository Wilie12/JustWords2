package com.willaapps.core.domain.user

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult

interface UserInfoRepository {

    suspend fun getUserInfo(userId: String): EmptyResult<DataError.Network>
    suspend fun setUserInfo(userInfo: UserInfo): EmptyResult<DataError.Network>
}