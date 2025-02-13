package com.battlecoach.data.local.dao

import androidx.room.*
import com.battlecoach.data.local.entities.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM games WHERE username = :username ORDER BY timestamp DESC")
    fun getGamesFlow(username: String): Flow<List<GameEntity>>

    @Query("SELECT * FROM games WHERE isSynced = 0")
    suspend fun getUnsyncedGames(): List<GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("UPDATE games SET isSynced = 1 WHERE id IN (:gameIds)")
    suspend fun markGamesAsSynced(gameIds: List<String>)

    @Query("DELETE FROM games WHERE username = :username AND timestamp < :timestamp")
    suspend fun deleteOldGames(username: String, timestamp: Long)
} 