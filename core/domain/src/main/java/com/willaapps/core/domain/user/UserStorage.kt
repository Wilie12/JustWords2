package com.willaapps.core.domain.user

import kotlinx.coroutines.flow.Flow

interface UserStorage {

    fun get(): Flow<UserInfo?>
    suspend fun setUserInfo(userInfo: UserInfo)
    suspend fun setIncreasedDailyGoal()
    suspend fun clearUserInfo()
}