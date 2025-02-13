package com.battlecoach.data.repository

import com.battlecoach.data.local.dao.GameDao
import com.battlecoach.data.local.entities.GameEntity
import com.battlecoach.domain.parser.PgnParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameImportRepository @Inject constructor(
    private val gameDao: GameDao,
    private val pgnParser: PgnParser
) {
    suspend fun importFromPgn(pgn: String, username: String) = withContext(Dispatchers.IO) {
        val games = pgnParser.parseFromString(pgn)
        val entities = games.map { game ->
            GameEntity(
                id = UUID.randomUUID().toString(),
                username = username,
                opponent = game.whitePlayer.name.takeIf { it != username }
                    ?: game.blackPlayer.name,
                result = game.result.value,
                pgn = game.pgn,
                timestamp = System.currentTimeMillis(),
                platform = "IMPORTED",
                timeControl = game.timeControl ?: "STANDARD",
                playerRating = game.whitePlayer.elo.takeIf { game.whitePlayer.name == username }
                    ?: game.blackPlayer.elo,
                opponentRating = game.whitePlayer.elo.takeIf { game.whitePlayer.name != username }
                    ?: game.blackPlayer.elo,
                isSynced = false
            )
        }
        gameDao.insertGames(entities)
    }

    suspend fun importFromUrl(url: String, username: String) = withContext(Dispatchers.IO) {
        val games = pgnParser.parseFromUrl(url)
        // Same conversion as above...
    }
} 