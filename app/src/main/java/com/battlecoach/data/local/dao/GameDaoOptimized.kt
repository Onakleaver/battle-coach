package com.battlecoach.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDaoOptimized {
    @Query("""
        SELECT * FROM games 
        WHERE username = :username 
        ORDER BY timestamp DESC 
        LIMIT :limit
    """)
    fun getRecentGames(username: String, limit: Int = 10): Flow<List<GameEntity>>

    @Query("""
        SELECT COUNT(*) as count, 
               SUM(CASE WHEN result = '1-0' THEN 1 ELSE 0 END) as wins,
               AVG(playerRating) as avgRating
        FROM games
        WHERE username = :username
        AND timestamp >= :startTime
    """)
    fun getPlayerStats(username: String, startTime: Long): Flow<PlayerStats>

    @Transaction
    suspend fun insertGameAndUpdateStats(game: GameEntity) {
        insertGame(game)
        updatePlayerRating(game.username, game.playerRating)
    }

    @Query("SELECT * FROM games WHERE isSynced = 0 LIMIT 50")
    suspend fun getUnsyncedGamesBatch(): List<GameEntity>
}

data class PlayerStats(
    val count: Int,
    val wins: Int,
    val avgRating: Float
) 