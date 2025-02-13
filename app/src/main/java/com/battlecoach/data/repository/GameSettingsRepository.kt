package com.battlecoach.data.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.battlecoach.domain.game.TimeControl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "game_settings")

@Singleton
class GameSettingsRepository @Inject constructor(
    private val context: Context
) {
    private object PreferencesKeys {
        val PREFERRED_TIME_CONTROL = stringPreferencesKey("preferred_time_control")
        val PREFERRED_BOT_STRENGTH = intPreferencesKey("preferred_bot_strength")
        val AUTO_SYNC_ENABLED = booleanPreferencesKey("auto_sync_enabled")
    }

    val gameSettings: Flow<GameSettings> = context.dataStore.data.map { preferences ->
        GameSettings(
            preferredTimeControl = TimeControl.valueOf(
                preferences[PreferencesKeys.PREFERRED_TIME_CONTROL] ?: "RAPID_15_10"
            ),
            preferredBotStrength = preferences[PreferencesKeys.PREFERRED_BOT_STRENGTH] ?: 0,
            autoSyncEnabled = preferences[PreferencesKeys.AUTO_SYNC_ENABLED] ?: true
        )
    }

    suspend fun updateTimeControl(timeControl: TimeControl) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERRED_TIME_CONTROL] = timeControl.toString()
        }
    }

    suspend fun updateBotStrength(strengthDelta: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PREFERRED_BOT_STRENGTH] = strengthDelta
        }
    }
}

data class GameSettings(
    val preferredTimeControl: TimeControl,
    val preferredBotStrength: Int,
    val autoSyncEnabled: Boolean
) 