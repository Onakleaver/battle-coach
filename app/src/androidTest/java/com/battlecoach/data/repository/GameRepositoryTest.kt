package com.battlecoach.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.battlecoach.data.local.database.BattleCoachDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameRepositoryTest {
    private lateinit var database: BattleCoachDatabase
    private lateinit var repository: GameRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            BattleCoachDatabase::class.java
        ).build()
        
        repository = GameRepository(
            gameDao = database.gameDao(),
            gameSyncService = MockGameSyncService(),
            settingsRepository = MockGameSettingsRepository()
        )
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun testOfflineGameStorage() = runBlocking {
        val game = GameEntity(
            id = "test_game",
            username = "player",
            opponent = "Stockfish",
            result = "1-0",
            pgn = "1. e4 e5",
            timestamp = System.currentTimeMillis(),
            platform = "LOCAL",
            timeControl = "RAPID",
            playerRating = 1500,
            opponentRating = 1800,
            isSynced = false
        )

        repository.saveGame(game)
        
        val savedGames = repository.getOfflineGames().first()
        assert(savedGames.contains(game))
    }
} 