package com.battlecoach.domain.engine

import com.battlecoach.data.local.dao.GameDao
import com.battlecoach.data.local.entities.GameEntity
import com.battlecoach.domain.engine.lczero.LCZeroEngine
import com.battlecoach.domain.engine.stockfish.StockfishEngine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChessEngineManager @Inject constructor(
    private val stockfish: StockfishEngine,
    private val lczero: LCZeroEngine,
    private val gameDao: GameDao
) {
    private val mutex = Mutex()
    private var currentEngine: ChessEngine = stockfish
    private var isInitialized = false

    suspend fun initialize() = mutex.withLock {
        if (!isInitialized) {
            stockfish.initialize()
            lczero.initialize()
            isInitialized = true
        }
    }

    suspend fun setEngine(useStockfish: Boolean) = mutex.withLock {
        currentEngine = if (useStockfish) stockfish else lczero
    }

    suspend fun setDifficulty(level: EngineLevel) = mutex.withLock {
        currentEngine.setDifficulty(level)
    }

    suspend fun analyze(
        fen: String,
        timeMs: Long = 1000,
        useStockfish: Boolean = true
    ): EngineAnalysis = mutex.withLock {
        val engine = if (useStockfish) stockfish else lczero
        engine.setPosition(fen)
        engine.analyze(timeMs)
    }

    suspend fun getBestMove(
        fen: String,
        timeMs: Long = 1000,
        useStockfish: Boolean = true
    ): String = mutex.withLock {
        analyze(fen, timeMs, useStockfish).bestMove
    }

    suspend fun storeGameResult(
        gameId: String,
        playerName: String,
        engineName: String,
        finalFen: String,
        pgn: String,
        result: String,
        engineLevel: EngineLevel
    ) {
        gameDao.insertGames(listOf(
            GameEntity(
                id = gameId,
                username = playerName,
                opponent = engineName,
                result = result,
                pgn = pgn,
                timestamp = System.currentTimeMillis(),
                platform = "LOCAL",
                timeControl = "STANDARD",
                playerRating = 0, // Calculate based on performance
                opponentRating = engineLevel.elo,
                isSynced = false
            )
        ))
    }

    suspend fun quit() = mutex.withLock {
        stockfish.quit()
        lczero.quit()
    }
} 