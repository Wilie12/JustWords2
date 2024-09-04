package com.willaapps.core.domain.user

import kotlinx.coroutines.flow.Flow

interface UserStorage {

    // TODO - add synchronization between local and server data
    // TODO - update userStorage from app and on server
    fun get(): Flow<UserInfo?>
    suspend fun setUserInfo(userInfo: UserInfo)
    suspend fun updateUsername(username: String)
    suspend fun increaseDailyGoal()
    suspend fun updateDailyGoal(newGoal: Int)
    suspend fun clearUserInfo()
}