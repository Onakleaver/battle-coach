package com.battlecoach.data.repository

import com.battlecoach.data.local.dao.GameDao
import com.battlecoach.data.local.entities.GameEntity
import com.battlecoach.data.remote.api.GameSyncService
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val gameDao: GameDao,
    private val gameSyncService: GameSyncService,
    private val settingsRepository: GameSettingsRepository
) {
    fun getOfflineGames() = gameDao.getAllGames()
    
    fun getUnsyncedGames() = gameDao.getUnsyncedGames()

    suspend fun saveGame(game: GameEntity) {
        gameDao.insertGame(game)
        
        // Try to sync if enabled
        settingsRepository.gameSettings.first().let { settings ->
            if (settings.autoSyncEnabled) {
                trySync()
            }
        }
    }

    suspend fun trySync() {
        try {
            val unsyncedGames = getUnsyncedGames().first()
            unsyncedGames.forEach { game ->
                gameSyncService.syncGame(game)
                gameDao.updateSyncStatus(game.id, true)
            }
        } catch (e: Exception) {
            // Handle offline case
        }
    }
} 