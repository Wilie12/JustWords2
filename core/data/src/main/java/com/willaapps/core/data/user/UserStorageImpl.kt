package com.willaapps.core.data.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.willaapps.core.data.user.dto.UserInfoSerializable
import com.willaapps.core.data.user.dto.toUserInfo
import com.willaapps.core.data.user.dto.toUserInfoSerializable
import com.willaapps.core.domain.user.UserInfo
import com.willaapps.core.domain.user.UserStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.ZoneId
import java.time.ZonedDateTime

class UserStorageImpl(
    private val dataStore: DataStore<Preferences>
) : UserStorage {
    override fun get(): Flow<UserInfo?> {
        return dataStore.data.map { preferences ->
            val userInfo = try {
                Json
                    .decodeFromString<UserInfoSerializable>(preferences[KEY_USER_INFO] ?: "")
                    .toUserInfo()
            } catch (e: Exception) {
                null
            }

            userInfo?.let { resetDailyIfNecessary(it) }

            try {
                Json.decodeFromString<UserInfoSerializable>(preferences[KEY_USER_INFO] ?: "")
                    .toUserInfo()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun setUserInfo(userInfo: UserInfo) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_INFO] = Json
                .encodeToString(userInfo.toUserInfoSerializable())
        }
    }

    override suspend fun setIncreasedDailyGoal() {
        dataStore.edit { preferences ->
            val currentUserInfo = Json
                .decodeFromString<UserInfoSerializable>(
                    preferences[KEY_USER_INFO] ?: return@edit
                )
                .toUserInfo()

            val newUserInfo = increaseDailyGoal(currentUserInfo)

            preferences[KEY_USER_INFO] = Json.encodeToString(newUserInfo.toUserInfoSerializable())
        }
    }

    override suspend fun clearUserInfo() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_INFO)
            preferences.clear()
            preferences[KEY_USER_INFO] = ""
        }
    }

    private suspend fun resetDailyIfNecessary(userInfo: UserInfo) {
        val dateItem = userInfo.getDateItem()

        val newDailyStreak = if (dateItem.today == dateItem.lastDayPlayed
            || dateItem.today == dateItem.nextDayAfterPlay
        ) {
            userInfo.dailyStreak
        } else {
            0
        }
        val newCurrentGoal =
            if (dateItem.today != dateItem.lastDayPlayed) 0 else userInfo.currentGoal

        if (newCurrentGoal == userInfo.currentGoal && newDailyStreak == userInfo.dailyStreak) {
            return
        }

        val newUserInfo = userInfo.copy(
            dailyStreak = newDailyStreak,
            currentGoal = newCurrentGoal
        )

        setUserInfo(newUserInfo)
    }

    private fun increaseDailyGoal(userInfo: UserInfo): UserInfo {
        val dateItem = userInfo.getDateItem()

        val newCurrentGoal =
            if (dateItem.today != dateItem.lastDayPlayed) 1 else userInfo.currentGoal + 1
        val newDailyStreak = if (dateItem.today == dateItem.lastDayPlayed) {
            if (userInfo.dailyStreak == 0) 1 else userInfo.dailyStreak
        } else if (dateItem.today != dateItem.nextDayAfterPlay) {
            1
        } else {
            userInfo.dailyStreak + 1
        }
        val newBestStreak =
            if (newDailyStreak > userInfo.bestStreak) newDailyStreak else userInfo.bestStreak

        return userInfo.copy(
            currentGoal = newCurrentGoal,
            dailyStreak = newDailyStreak,
            bestStreak = newBestStreak,
            lastPlayedTimestamp = ZonedDateTime.now()
                .withZoneSameInstant(ZoneId.of("UTC"))
        )
    }

    companion object {
        private val KEY_USER_INFO = stringPreferencesKey("KEY_USER_INFO")
    }
}