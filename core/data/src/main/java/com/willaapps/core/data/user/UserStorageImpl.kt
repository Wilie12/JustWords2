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
import timber.log.Timber
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

            userInfo?.let { setUserInfo(resetDailyIfNecessary(it)) }
            Timber.d("USER INFO CHANGED")

            preferences[KEY_USER_INFO]?.let {
                Json.decodeFromString<UserInfoSerializable>(it).toUserInfo()
            }
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

    private fun resetDailyIfNecessary(userInfo: UserInfo): UserInfo {
        val lastDayPlayed = userInfo.lastPlayedTimestamp
            .withZoneSameInstant(ZoneId.of("UTC"))?.dayOfMonth
        val nextDayAfterPlay = userInfo.lastPlayedTimestamp
            .withZoneSameInstant(ZoneId.of("UTC"))?.plusDays(1)?.dayOfMonth
        val today = ZonedDateTime.now()
            .withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth

        val newDailyStreak = if (today == lastDayPlayed || today == nextDayAfterPlay) {
            userInfo.dailyStreak
        } else {
            0
        }

        return userInfo.copy(
            dailyStreak = newDailyStreak,
            currentGoal = if (today != lastDayPlayed) 0 else userInfo.currentGoal
        )
    }

    private fun increaseDailyGoal(userInfo: UserInfo): UserInfo {
        val lastDayPlayed = userInfo.lastPlayedTimestamp
            .withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth
        val nextDayAfterPlay = userInfo.lastPlayedTimestamp
            .withZoneSameInstant(ZoneId.of("UTC")).plusDays(1).dayOfMonth
        val today = ZonedDateTime.now()
            .withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth

        val newCurrentGoal = if (today != lastDayPlayed) 1 else userInfo.currentGoal + 1
        val newDailyStreak = if (today == lastDayPlayed) {
            if (userInfo.dailyStreak == 0) 1 else userInfo.dailyStreak
        } else if (today != nextDayAfterPlay) {
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