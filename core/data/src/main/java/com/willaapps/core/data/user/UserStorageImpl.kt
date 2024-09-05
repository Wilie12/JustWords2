package com.willaapps.core.data.user

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.willaapps.core.domain.user.UserInfo
import com.willaapps.core.domain.user.UserStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.ZonedDateTime

class UserStorageImpl(
    private val dataStore: DataStore<Preferences>
) : UserStorage {
    override fun get(): Flow<UserInfo?> {
        return dataStore.data.map { preferences ->
            val userInfo = Json
                .decodeFromString<UserInfoSerializable>(preferences[KEY_USER_INFO] ?: "")
                .toUserInfo()

            val lastDayPlayed = userInfo.lastPlayedTimestamp.dayOfMonth
            val nextDayAfterPlay = userInfo.lastPlayedTimestamp.plusDays(1).dayOfMonth
            val today = ZonedDateTime.now().dayOfMonth
            val newUserInfo = userInfo.copy(
                dailyStreak = if (today != nextDayAfterPlay) 0 else userInfo.dailyStreak,
                currentGoal = if (today != lastDayPlayed) 0 else userInfo.currentGoal
            )
            setUserInfo(newUserInfo)

            preferences[KEY_USER_INFO]?.let { Json.decodeFromString<UserInfoSerializable>(it).toUserInfo() }
        }
    }

    override suspend fun setUserInfo(userInfo: UserInfo) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_INFO] = Json
                .encodeToString(userInfo.toUserInfoSerializable())
        }
    }

    override suspend fun updateUsername(username: String) {
        dataStore.edit { preferences ->
            val currentUserInfo = Json
                .decodeFromString<UserInfoSerializable>(
                    preferences[KEY_USER_INFO] ?: return@edit
                )
            val newUserInfo = currentUserInfo.copy(
                username = username
            )
            preferences[KEY_USER_INFO] = Json.encodeToString(newUserInfo)
        }
    }

    override suspend fun increaseDailyGoal() {
        dataStore.edit { preferences ->
            val currentUserInfo = Json
                .decodeFromString<UserInfoSerializable>(
                    preferences[KEY_USER_INFO] ?: return@edit
                )
                .toUserInfo()

            val lastDayPlayed = currentUserInfo.lastPlayedTimestamp.dayOfMonth
            val nextDayAfterPlay = currentUserInfo.lastPlayedTimestamp.plusDays(1).dayOfMonth
            val today = ZonedDateTime.now().dayOfMonth

            val newCurrentGoal = if (today != lastDayPlayed) 1 else currentUserInfo.currentGoal + 1
            val newDailyStreak = if (today == lastDayPlayed) {
                currentUserInfo.dailyStreak
            } else if (today != nextDayAfterPlay) {
                1
            } else {
                currentUserInfo.dailyStreak + 1
            }
            val newBestStreak =
                if (newDailyStreak > currentUserInfo.bestStreak) newDailyStreak else currentUserInfo.bestStreak

            val newUserInfo = currentUserInfo.copy(
                currentGoal = newCurrentGoal,
                dailyGoal = newDailyStreak,
                bestStreak = newBestStreak
            )

            preferences[KEY_USER_INFO] = Json.encodeToString(newUserInfo.toUserInfoSerializable())
        }
    }

    override suspend fun updateDailyGoal(newGoal: Int) {
        dataStore.edit { preferences ->
            val currentUserInfo = Json
                .decodeFromString<UserInfoSerializable>(
                    preferences[KEY_USER_INFO] ?: return@edit
                )
            val newUserInfo = currentUserInfo.copy(
                dailyGoal = newGoal
            )
            preferences[KEY_USER_INFO] = Json.encodeToString(newUserInfo)
        }
    }

    override suspend fun clearUserInfo() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_INFO)
        }
    }

    companion object {
        private val KEY_USER_INFO = stringPreferencesKey("KEY_USER_INFO")
    }
}