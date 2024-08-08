package com.willaapps.word.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.willaapps.word.domain.PreviousWord
import com.willaapps.word.domain.PreviousWordStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PreviousWordStorageImpl(
    private val dataStore: DataStore<Preferences>
): PreviousWordStorage {

    override fun get(): Flow<PreviousWord?> {

        return dataStore.data.map { preferences ->
            preferences[KEY_PREVIOUS_WORD]
                ?.let { Json.decodeFromString<PreviousWordSerializable>(it).toPreviousWord() }
        }
    }

    override suspend fun set(previousWord: PreviousWord?) {

        if (previousWord == null) {
            dataStore.edit { preferences ->
                preferences.clear()
            }
            return
        }

        val json = Json.encodeToString(previousWord.toPreviousWordSerializable())

        dataStore.edit { settings ->
            settings[KEY_PREVIOUS_WORD] = json
        }
    }

    companion object {
        private val KEY_PREVIOUS_WORD = stringPreferencesKey("KEY_PREVIOUS_WORD")
    }
}