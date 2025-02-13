package com.battlecoach.data.repository

import com.battlecoach.data.local.dao.GameDao
import com.battlecoach.data.local.dao.UserDao
import com.battlecoach.data.local.entities.GameEntity
import com.battlecoach.data.local.entities.UserEntity
import com.battlecoach.data.remote.dto.ChessGame
import com.battlecoach.data.remote.dto.ChessUserStats
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameHistoryRepository @Inject constructor(
    private val gameDao: GameDao,
    private val userDao: UserDao,
    private val chessStatsRepository: ChessStatsRepository
) {
    fun getUserGamesFlow(username: String): Flow<List<GameEntity>> =
        gameDao.getGamesFlow(username)

    fun getUserFlow(username: String): Flow<UserEntity?> =
        userDao.getUserFlow(username)

    suspend fun syncUserData(username: String) {
        try {
            val stats = chessStatsRepository.getUserStats(username)
            
            // Update user data
            val user = UserEntity(
                username = username,
                chessComRating = stats.firstOrNull { it.platform.name == "CHESS_COM" }
                    ?.ratings?.rapid ?: 0,
                lichessRating = stats.firstOrNull { it.platform.name == "LICHESS" }
                    ?.ratings?.rapid ?: 0,
                puzzleRating = stats.maxOf { it.ratings.puzzle },
                lastSyncTimestamp = System.currentTimeMillis()
            )
            userDao.insertUser(user)

            // Update games
            val games = stats.flatMap { platformStats ->
                platformStats.recentGames.map { game ->
                    GameEntity(
                        id = game.id,
                        username = username,
                        opponent = game.opponent,
                        result = game.result.name,
                        pgn = game.moves ?: "",
                        timestamp = game.date,
                        platform = platformStats.platform.name,
                        timeControl = game.timeControl,
                        playerRating = when (game.result) {
                            GameResult.WIN -> game.opponentRating + 1
                            GameResult.LOSS -> game.opponentRating - 1
                            GameResult.DRAW -> game.opponentRating
                        },
                        opponentRating = game.opponentRating,
                        isSynced = true
                    )
                }
            }
            gameDao.insertGames(games)

            // Clean up old games (keep last 6 months)
            val sixMonthsAgo = System.currentTimeMillis() - (180L * 24 * 60 * 60 * 1000)
            gameDao.deleteOldGames(username, sixMonthsAgo)
        } catch (e: Exception) {
            // Handle error
            throw e
        }
    }
} 